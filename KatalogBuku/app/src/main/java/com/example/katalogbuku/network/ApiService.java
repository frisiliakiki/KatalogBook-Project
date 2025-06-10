package com.example.katalogbuku.network;

import android.util.Log;
import com.example.katalogbuku.data.model.Book;
import com.example.katalogbuku.data.model.BookApiResponse;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiService {
    private static final String TAG = "ApiService";
    // Ganti dengan API Key RapidAPI baru Anda
    private static final String API_KEY = "b7184f756dmshcca63c380fefd9ep1cb1cajsna8da80784e38";
    private static final String BASE_URL = "https://books-recommendation-270k-books.p.rapidapi.com/list";

    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface BooksApiCallback {
        void onSuccess(List<Book> books);
        void onFailure(Exception e);
    }

    public void fetchBooks(BooksApiCallback callback) {
        executor.execute(() -> {
            try {
                // Membuat request body dan request POST sesuai permintaan
                MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
                RequestBody body = RequestBody.create(mediaType, "page=1&year=2000");
                Request request = new Request.Builder()
                        .url(BASE_URL)
                        .post(body)
                        .addHeader("x-rapidapi-key", API_KEY)
                        .addHeader("x-rapidapi-host", "books-recommendation-270k-books.p.rapidapi.com")
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .build();

                Response response = client.newCall(request).execute();

                if (response.isSuccessful() && response.body() != null) {
                    String jsonString = response.body().string();
                    Log.d(TAG, "RapidAPI Response: " + jsonString);
                    BookApiResponse apiResponse = gson.fromJson(jsonString, BookApiResponse.class);

                    if (apiResponse != null && apiResponse.getResult() != null) {
                        List<Book> books = apiResponse.getResult().getData();
                        callback.onSuccess(books);
                    } else {
                        callback.onFailure(new IOException("Respons JSON tidak valid atau kosong."));
                    }

                } else {
                    Log.e(TAG, "API call failed with code: " + response.code());
                    callback.onFailure(new IOException("Unexpected code " + response));
                }
            } catch (Exception e) {
                Log.e(TAG, "Exception during API call", e);
                callback.onFailure(e);
            }
        });
    }
}
