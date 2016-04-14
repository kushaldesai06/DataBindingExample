package com.example.kushaldesai.demoapp.API;

import com.example.kushaldesai.demoapp.model.SearchResults;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Kushal.Desai on 4/14/2016.
 */
public interface GoogleBooksService {
    @GET("/books/v1/volumes")
    void search(@Query("q") String query, Callback<SearchResults> callback);
}
