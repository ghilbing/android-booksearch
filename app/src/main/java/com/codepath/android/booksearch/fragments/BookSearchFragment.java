package com.codepath.android.booksearch.fragments;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.codepath.android.booksearch.ListDivider.ListDivider;
import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.activities.MainActivity;
import com.codepath.android.booksearch.adapters.BooksAdapter;
import com.codepath.android.booksearch.models.Book;
import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.JsonKeys;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.cache.Resource;

import static com.codepath.android.booksearch.R.id.lvBooks;

/**
 * Created by gretel on 10/19/17.
 */

public class BookSearchFragment extends BaseBookListFragment  {

    public static String BOOKS_LIST = "books";
    public ArrayList<Book> books;

    private BookClient client;

    public BooksAdapter aBooks;
    private RecyclerView rvBooks;
    private LinearLayoutManager lyManager;
    private BookListClickListener clickListener;
    protected BookListReadyListener readyListener;
    private ProgressBar mProgressBar;

    private Toolbar toolbar;

    public BookSearchFragment(){

    }

    public static BookSearchFragment newInstance(){
        BookSearchFragment fragment = new BookSearchFragment();
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setHasOptionsMenu(true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_search, container, false);

        this.setHasOptionsMenu(true);

        if (books == null){
            books = new ArrayList<>();
        }

        toolbar = (Toolbar) view.findViewById(R.id.app_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        rvBooks = (RecyclerView) view.findViewById(R.id.rvBooksSearch);


        setupRecyclerView();
        //setupFooter();

        return view;

    }


        public void fetchBooks (String query){
            client = new BookClient();
            client.getBooks(query, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONArray docs;
                        if (response != null) {
                            docs = response.getJSONArray(JsonKeys.DOCS);
                            Log.i("DOCS", docs.toString());

                            final ArrayList<Book> books = Book.fromJson(docs);

                            pushData(books);
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
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fetchBooks(query);
                searchView.clearFocus();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }


    private void setupRecyclerView() {
        aBooks = new BooksAdapter(getContext(), books);
        aBooks.setListener(this);
        rvBooks.setAdapter(aBooks);
        lyManager = new LinearLayoutManager(getContext());
        rvBooks.setLayoutManager(lyManager);


        //Line between rows
        ListDivider line = new ListDivider(getContext());
        rvBooks.addItemDecoration(line);
    }

}
