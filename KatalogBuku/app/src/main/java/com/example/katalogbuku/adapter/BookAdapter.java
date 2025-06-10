package com.example.katalogbuku.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.katalogbuku.R;
import com.example.katalogbuku.data.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final List<Book> bookList = new ArrayList<>();
    private OnBookItemClickListener listener;
    public interface OnBookItemClickListener {
        void onBookItemClick(Book book);
    }

    public void setOnBookItemClickListener(OnBookItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bind(book);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public void setBooks(List<Book> books) {
        bookList.clear();
        bookList.addAll(books);
        notifyDataSetChanged();
    }

    public void filterList(List<Book> filteredList) {
        bookList.clear();
        bookList.addAll(filteredList);
        notifyDataSetChanged();
    }

    class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookCover;
        TextView tvBookTitle;
        TextView tvBookAuthor;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.iv_book_cover);
            tvBookTitle = itemView.findViewById(R.id.tv_book_title);
            tvBookAuthor = itemView.findViewById(R.id.tv_book_author);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                // Pastikan listener ada dan posisi valid sebelum memanggil klik
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onBookItemClick(bookList.get(position));
                }
            });
        }

        public void bind(Book book) {
            tvBookTitle.setText(book.getBookTitle());
            tvBookAuthor.setText(book.getBookAuthor());

            Glide.with(itemView.getContext())
                    .load(book.getImageUrlM())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_foreground)
                    .into(ivBookCover);
        }
    }
}