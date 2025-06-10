package com.example.katalogbuku.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.example.katalogbuku.data.model.Book;
import java.util.List;

@Dao
public interface BookDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Book> books);

    @Query("SELECT * FROM books_table")
    List<Book> getAllBooks();

    @Query("DELETE FROM books_table")
    void deleteAll();

    @Update
    void update(Book book);

    @Query("SELECT * FROM books_table WHERE isFavorite = 1")
    List<Book> getFavoriteBooks();

    @Query("SELECT * FROM books_table WHERE isbn = :isbn")
    Book getBookByIsbn(String isbn);

}