package com.example.kushaldesai.demoapp;

import android.app.Application;
import com.example.kushaldesai.demoapp.model.Book;
import java.util.List;

/**
 * Created by Kushal.Desai on 4/14/2016.
 */
public class DataBindingApplication extends Application{

    List<Book> mBooks;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public List<Book> getBooks() {
        return mBooks;
    }

    public void setBooks(List<Book> mResultsist) {
        this.mBooks = mResultsist;
    }
}
