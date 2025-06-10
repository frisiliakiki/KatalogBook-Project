package com.example.katalogbuku.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BookApiResponse {

    @SerializedName("result")
    private ResultObject result;

    public ResultObject getResult() {
        return result;
    }

    public static class ResultObject {
        @SerializedName("current_page")
        private int currentPage;

        @SerializedName("data")
        private List<Book> data; // Daftar buku pada reyclerview

        public int getCurrentPage() {
            return currentPage;
        }

        public List<Book> getData() {
            return data;
        }
    }
}

