package com.example.kushaldesai.demoapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResults {
    public int totalItems;
    @SerializedName("items")
    public List<Book> books;
}
