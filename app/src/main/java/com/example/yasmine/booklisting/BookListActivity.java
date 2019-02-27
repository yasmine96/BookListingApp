package com.example.yasmine.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BookListActivity extends AppCompatActivity
        implements LoaderCallbacks<List<BookList>> {


    private BookListAdapter mAdapter;
    private TextView mEmptyTextView;
    private EditText mEditText;
    public String searchString;
    private ProgressBar progressBar;

    public static final String LOG_TAG = BookListActivity.class.getName();
    public static final int LOADER_ID = 1;
    public static  String BOOKS_URL =  "https://www.googleapis.com/books/v1/volumes?maxResults=20&q=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booklist_activity);

        mEditText=(EditText)findViewById(R.id.search_edit_text);


        ImageView searchButton = (ImageView) findViewById(R.id.search_button);

        final ListView booksListView = (ListView) findViewById(R.id.list);

        mEmptyTextView = (TextView) findViewById(R.id.empty_view);

        progressBar =(ProgressBar) findViewById(R.id.loading_indicator);

        booksListView.setEmptyView(mEmptyTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new BookListAdapter(this, new ArrayList<BookList>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        booksListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected book.
        booksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Find the current book that was clicked on
                BookList currentBook = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri bookUri = Uri.parse(currentBook.getUrl());

                // Create a new intent to view the book URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookUri);


                if(websiteIntent.resolveActivity(getPackageManager())!= null){
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }

            }

        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mEditText.clearFocus();
                //mAdapter.notifyDataSetChanged();

                String searchQuery = mEditText.getText().toString().replaceAll(" ", "+");
                if (searchQuery != null && !searchQuery.equals("")) {

                    searchString = BOOKS_URL + searchQuery;

                    mEmptyTextView.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    // Get a reference to the ConnectivityManager to check state of network connectivity
                    ConnectivityManager connMangr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

                    // Get details on the currently active default data network
                    NetworkInfo networkInfo = connMangr.getActiveNetworkInfo();

                    // If there is a network connection, fetch data
                    if (networkInfo != null && networkInfo.isConnected()) {
                        // Get a reference to the LoaderManager, in order to interact with loaders.
                        LoaderManager loaderManager = getLoaderManager();

                        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                        // because this activity implements the LoaderCallbacks interface).
                        loaderManager.restartLoader(LOADER_ID, null, BookListActivity.this);
                    } else  {
                        // Otherwise, display error
                        // First, hide loading indicator so error message will be visible
                        View loadingIndicator = findViewById(R.id.loading_indicator);
                        loadingIndicator.setVisibility(View.GONE);

                        // Update empty state with no connection error message
                        mEmptyTextView.setText("No Internet Connection");
                    }

                }
            }
        });

    }


    @Override
    public Loader<List<BookList>> onCreateLoader(int i,Bundle bundle)
    {
        return  new BookListLoader(this,searchString);
    }

    @Override
    public void onLoadFinished(Loader<List<BookList>> loader, List<BookList> data) {

        // Hide loading indicator because the data has been loaded
        progressBar.setVisibility(View.GONE);

        mEmptyTextView.setVisibility(View.GONE);
        // Set empty state text to display "No Books found."
        //mEmptyTextView.setText("No Books found");

        // Clear the adapter of previous book data
        mAdapter.clear();

        // If there is a valid list of {@link Booklist}, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);

        }


    }

    @Override
    public void onLoaderReset(Loader<List<BookList>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
