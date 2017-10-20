package com.codepath.android.booksearch.activities;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.adapters.BookAdapter;
import com.codepath.android.booksearch.models.Book;
import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.JsonKeys;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;


public class BookListActivity extends AppCompatActivity {
    private ListView lvBooks;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ArrayList<Book> aBooks;
    private ProgressBar mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        ButterKnife.bind(this);

        lvBooks = (ListView) findViewById(R.id.lvBooks);
        aBooks = new ArrayList<>();

        bookAdapter = new BookAdapter(this, aBooks);

        setupListWithFooter();


        lvBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                launchBookDetailsActivity(position);
            }
        });

    }

    public void setupListWithFooter() {
        View footerView = getLayoutInflater().inflate(R.layout.footer_progress, null);
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.pbFooterLoading);
        lvBooks.addFooterView(footerView);
        lvBooks.setAdapter(bookAdapter);

    }

    public void launchBookDetailsActivity(int position) {
        Book book = aBooks.get(position);

        Intent i = new Intent(this, BookDetailActivity.class);
        i.putExtra("book", Parcels.wrap(book));
        startActivity(i);

    }


    private void fetchBooks(String query) {
        client = new BookClient();
        client.getBooks(query, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray docs;
                    if(response != null) {

                        docs = response.getJSONArray(JsonKeys.DOCS);

                        final ArrayList<Book> books = Book.fromJson(docs);

                        bookAdapter.clear();

                        for (Book book : books) {
                            bookAdapter.add(book);
                        }
                        bookAdapter.notifyDataSetChanged();
                        hideProgress();
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenuItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                showProgress();
                fetchBooks(query);

                searchView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();



        return super.onOptionsItemSelected(item);
    }


    void showProgress() {
        Toast.makeText(getApplicationContext(), "Starting progress", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.VISIBLE);
    }


    void hideProgress() {
        Toast.makeText(getApplicationContext(), "Stopping progress", Toast.LENGTH_SHORT).show();
        mProgressBar.setVisibility(View.INVISIBLE);
    }
}
