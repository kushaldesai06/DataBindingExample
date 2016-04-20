package com.example.kushaldesai.demoapp;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kushaldesai.demoapp.API.GoogleBooksService;
import com.example.kushaldesai.demoapp.Adapters.BooksRecyclerAdapter;
import com.example.kushaldesai.demoapp.Listners.RecyclerItemClickListener;
import com.example.kushaldesai.demoapp.databinding.ActivityMainBinding;
import com.example.kushaldesai.demoapp.model.SearchResults;
import com.example.testlibproject.BasicCalculations;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @InjectView(R.id.search_edit_text)
    protected EditText mSearchEditText;

    @InjectView(R.id.progress_bar)
    protected ProgressBar mProgressBar;

    @InjectView(R.id.search_results_recycler_view)
    protected RecyclerView mSearchResultsRecyclerView;

    private GoogleBooksService mGoogleBooksService;
    private BooksRecyclerAdapter mBooksRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.inject(this);
        mSearchResultsRecyclerView = (RecyclerView) findViewById(R.id.search_results_recycler_view);
        mSearchResultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        init();
    }

    // Initialize the Rest Adapter as well as the GoogleBooksService for making call to API using retrofit
    private void init() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://www.googleapis.com")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        mGoogleBooksService = restAdapter.create(GoogleBooksService.class);
        Log.e("Test Lib","Date Formated = "+ BasicCalculations.ConvertDate("20 Apr 2016 02:23:44"));
    }

    @OnClick(R.id.search_button)
    public void onSearchButtonClicked() {
        handleSearchRequest();
    }

    @OnEditorAction(R.id.search_edit_text)
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            handleSearchRequest();
            return true;
        }
        return false;
    }

    private void handleSearchRequest() {
        String query = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(query)) {
            mSearchEditText.setError("Required");
        } else {
            searchBooksByQuery(query);
        }
    }

    // Query to the google by using retrofit , and getting back the data as a SerachResults in callback
    private void searchBooksByQuery(String query) {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = this.getCurrentFocus();
        if (view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        displayProgress(true);
        mGoogleBooksService.search(query, mSearchResultsCallback);
    }

    private Callback<SearchResults> mSearchResultsCallback = new Callback<SearchResults>() {
        @Override
        public void success(SearchResults searchResults, Response response) {
            displayResults(searchResults);
        }

        @Override
        public void failure(RetrofitError error) {
            displayError(error);
        }
    };

    //Display results , Setting up adapter to the recylerView.
    private void displayResults(SearchResults searchResults) {
        Timber.d("Total search results: " + searchResults.totalItems);
        Timber.d("Got results: " + searchResults.books.size());

        displayProgress(false);

        if (mBooksRecyclerAdapter == null) {
            mBooksRecyclerAdapter = new BooksRecyclerAdapter(searchResults.books);
            mSearchResultsRecyclerView.setAdapter(mBooksRecyclerAdapter);

            ((DataBindingApplication) getApplication()).setBooks(searchResults.books);
        } else {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            ((DataBindingApplication) getApplication()).getBooks().addAll(searchResults.books);
            mBooksRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void displayError(RetrofitError error) {
        Timber.e("Search failed with error: " + error.getKind());

        displayProgress(false);

        if (((DataBindingApplication) getApplication()).getBooks() != null) {
            ((DataBindingApplication) getApplication()).getBooks().clear();
            if (mBooksRecyclerAdapter != null) {
                mBooksRecyclerAdapter.notifyDataSetChanged();
            }
        }
        if (error.getKind().equals(RetrofitError.Kind.NETWORK)) {
            Toast.makeText(this, "NetWork is not available", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this,"NetWork is not available", Toast.LENGTH_LONG).show();
        }
    }

    private void displayProgress(boolean show) {
        if (show) {
            mSearchResultsRecyclerView.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mSearchResultsRecyclerView.setVisibility(View.VISIBLE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

}
