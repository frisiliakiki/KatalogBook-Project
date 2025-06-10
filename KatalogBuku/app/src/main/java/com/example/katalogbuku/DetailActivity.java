package com.example.katalogbuku;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.katalogbuku.data.local.AppDatabase;
import com.example.katalogbuku.data.local.BookDao;
import com.example.katalogbuku.data.model.Book;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DetailActivity extends AppCompatActivity {

    private BookDao bookDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private boolean isFavorite = false;
    private ImageButton btnFavorite;
    private Book currentBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Inisialisasi Database dan Views
        bookDao = AppDatabase.getDatabase(this).bookDao();
        btnFavorite = findViewById(R.id.btn_favorite);
        ImageView ivCover = findViewById(R.id.iv_book_cover_detail);
        TextView tvTitle = findViewById(R.id.tv_book_title_detail);
        TextView tvAuthor = findViewById(R.id.tv_book_author_detail);
        TextView tvPublisher = findViewById(R.id.tv_publisher);
        TextView tvYear = findViewById(R.id.tv_year);
        TextView tvIsbn = findViewById(R.id.tv_isbn);

        // Ambil objek Book dari Intent
        currentBook = (Book) getIntent().getSerializableExtra("EXTRA_BOOK");

        // Jika buku tidak null, tampilkan datanya
        if (currentBook != null) {
            tvTitle.setText(currentBook.getBookTitle());
            tvAuthor.setText("Oleh: " + currentBook.getBookAuthor());
            tvPublisher.setText(currentBook.getPublisher());
            tvYear.setText(currentBook.getYearOfPublication());
            tvIsbn.setText(currentBook.getIsbn());

            // Muat gambar sampul
            Glide.with(this)
                    .load(currentBook.getImageUrlM())
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(ivCover);

            // Cek status favorit dari database
            checkFavoriteStatus();

            // Atur listener untuk tombol favorit
            btnFavorite.setOnClickListener(v -> {
                toggleFavoriteStatus();
            });
        }
    }

    private void checkFavoriteStatus() {
        executor.execute(() -> {
            Book bookFromDb = bookDao.getBookByIsbn(currentBook.getIsbn());
            if (bookFromDb != null) {
                isFavorite = bookFromDb.isFavorite();
                // Update UI harus di Main Thread
                runOnUiThread(() -> setFavoriteIcon(isFavorite));
            }
        });
    }

    private void toggleFavoriteStatus() {
        isFavorite = !isFavorite; // Balik status favorit
        currentBook.setFavorite(isFavorite);
        // Update buku di database di background thread
        executor.execute(() -> bookDao.update(currentBook));
        // Update tampilan tombol bintang
        setFavoriteIcon(isFavorite);
    }

    private void setFavoriteIcon(boolean isFavorite) {
        if (isFavorite) {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_on);
        } else {
            btnFavorite.setImageResource(android.R.drawable.btn_star_big_off);
        }
    }
}