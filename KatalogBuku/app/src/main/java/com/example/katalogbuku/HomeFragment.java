package com.example.katalogbuku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.katalogbuku.adapter.BookAdapter;
import com.example.katalogbuku.data.local.AppDatabase;
import com.example.katalogbuku.data.local.BookDao;
import com.example.katalogbuku.data.model.Book;
import com.example.katalogbuku.network.ApiService;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class HomeFragment extends Fragment implements BookAdapter.OnBookItemClickListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ProgressBar progressBar;
    private ApiService apiService;
    private BookDao bookDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    // --- VARIABEL BARU UNTUK PENCARIAN ---
    private SearchView searchView;
    private final List<Book> fullBookList = new ArrayList<>(); // Untuk menyimpan daftar asli

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi yang sudah ada
        recyclerView = view.findViewById(R.id.recycler_view_books);
        progressBar = view.findViewById(R.id.progress_bar);
        apiService = new ApiService();
        bookAdapter = new BookAdapter();
        bookDao = AppDatabase.getDatabase(requireContext()).bookDao();

        searchView = view.findViewById(R.id.search_view);

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.setOnBookItemClickListener(this);
        setupSearch();

        loadBooksFromCache();
        fetchBooksFromApi();
    }

    private void loadBooksFromCache() {
        progressBar.setVisibility(View.VISIBLE);
        executor.execute(() -> {
            List<Book> cachedBooks = bookDao.getAllBooks();
            if (isAdded()) {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (cachedBooks != null && !cachedBooks.isEmpty()) {
                        // --- PERBARUI LOGIKA: Simpan ke daftar asli ---
                        fullBookList.clear();
                        fullBookList.addAll(cachedBooks);
                        bookAdapter.setBooks(fullBookList);
                    }
                    progressBar.setVisibility(View.GONE);
                });
            }
        });
    }

    private void fetchBooksFromApi() {
        progressBar.setVisibility(View.VISIBLE);
        apiService.fetchBooks(new ApiService.BooksApiCallback() {
            @Override
            public void onSuccess(List<Book> newBooks) {
                executor.execute(() -> {
                    List<Book> favoriteBooks = bookDao.getFavoriteBooks();
                    Set<String> favoriteIsbns = favoriteBooks.stream()
                            .map(Book::getIsbn)
                            .collect(Collectors.toSet());

                    for (Book book : newBooks) {
                        if (favoriteIsbns.contains(book.getIsbn())) {
                            book.setFavorite(true);
                        }
                    }

                    bookDao.deleteAll();
                    bookDao.insertAll(newBooks);

                    if (isAdded()) {
                        new Handler(Looper.getMainLooper()).post(() -> {
                            progressBar.setVisibility(View.GONE);
                            fullBookList.clear();
                            fullBookList.addAll(newBooks);
                            bookAdapter.setBooks(fullBookList);
                            Toast.makeText(getContext(), "Data berhasil diperbarui", Toast.LENGTH_SHORT).show();
                        });
                    }
                });
            }

            @Override
            public void onFailure(Exception e) {
                if (isAdded()) {
                    new Handler(Looper.getMainLooper()).post(() -> {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Gagal terhubung. Menampilkan data offline.", Toast.LENGTH_LONG).show();
                    });
                }
            }
        });
    }

    private void setupSearch() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
    }

    private void filter(String text) {
        List<Book> filteredList = new ArrayList<>();
        // Lakukan iterasi pada daftar buku asli yang tidak terfilter
        for (Book item : fullBookList) {
            // mengecek apakah judul atau penulis mengandung teks pencarian
            if (item.getBookTitle().toLowerCase().contains(text.toLowerCase()) ||
                    item.getBookAuthor().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        // mengirim hasil filter ke adapter untuk ditampilkan
        bookAdapter.filterList(filteredList);
    }

    @Override
    public void onBookItemClick(Book book) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("EXTRA_BOOK", book);
        startActivity(intent);
    }
}