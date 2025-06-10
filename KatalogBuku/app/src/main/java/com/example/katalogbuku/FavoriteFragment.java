package com.example.katalogbuku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FavoriteFragment extends Fragment implements BookAdapter.OnBookItemClickListener {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookDao bookDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private TextView tvEmptyState;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_books);
        tvEmptyState = view.findViewById(R.id.tv_empty_state);
        bookDao = AppDatabase.getDatabase(requireContext()).bookDao();
        bookAdapter = new BookAdapter();

        // Setup RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(bookAdapter);
        bookAdapter.setOnBookItemClickListener(this);
    }

    // onResume dipanggil setiap kali fragment ini menjadi terlihat oleh pengguna
    @Override
    public void onResume() {
        super.onResume();
        // Muat ulang daftar favorit untuk memastikan data selalu terbaru
        loadFavoriteBooks();
    }

    private void loadFavoriteBooks() {
        executor.execute(() -> {
            // Ambil daftar buku favorit dari database di background thread
            List<Book> favoriteBooks = bookDao.getFavoriteBooks();

            // Kirim hasil ke Main Thread untuk memperbarui UI
            new Handler(Looper.getMainLooper()).post(() -> {
                // Cek apakah daftar favorit kosong atau tidak
                if (favoriteBooks != null && !favoriteBooks.isEmpty()) {
                    recyclerView.setVisibility(View.VISIBLE);
                    tvEmptyState.setVisibility(View.GONE);
                    bookAdapter.setBooks(favoriteBooks);
                } else {
                    // Jika kosong, sembunyikan daftar dan tampilkan pesan
                    recyclerView.setVisibility(View.GONE);
                    tvEmptyState.setVisibility(View.VISIBLE);
                }
            });
        });
    }

    //dipanggil saat item di daftar diklik
    @Override
    public void onBookItemClick(Book book) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra("EXTRA_BOOK", book);
        startActivity(intent);
    }
}