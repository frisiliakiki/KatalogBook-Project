package com.example.katalogbuku.data.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import androidx.room.ColumnInfo;
import java.io.Serializable;

@Entity(tableName = "books_table")
public class Book implements Serializable {

    @ColumnInfo(defaultValue = "0") // Default value 0 untuk false
    private boolean isFavorite = false;
    @PrimaryKey
    @NonNull
    @SerializedName("ISBN")
    private String isbn;

    @SerializedName("BookTitle")
    private String bookTitle;

    @SerializedName("BookAuthor")
    private String bookAuthor;

    @SerializedName("YearOfPublication")
    private String yearOfPublication;

    @SerializedName("Publisher")
    private String publisher;

    @SerializedName("ImageURLM")
    private String imageUrlM;

    public Book() {
        this.isbn = "";
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
    // --- GETTERS (Untuk mengambil data dari objek) ---
    @NonNull
    public String getIsbn() { return isbn; }
    public String getBookTitle() { return bookTitle; }
    public String getBookAuthor() { return bookAuthor; }
    public String getYearOfPublication() { return yearOfPublication; }
    public String getPublisher() { return publisher; }
    public String getImageUrlM() { return imageUrlM; }

    // --- SETTERS (untuk memasukkan data ke objek) ---
    public void setIsbn(@NonNull String isbn) { this.isbn = isbn; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }
    public void setYearOfPublication(String yearOfPublication) { this.yearOfPublication = yearOfPublication; }
    public void setPublisher(String publisher) { this.publisher = publisher; }
    public void setImageUrlM(String imageUrlM) { this.imageUrlM = imageUrlM; }

}