package com.codepath.android.booksearch.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.adapters.BookAdapter;
import com.codepath.android.booksearch.fragments.BaseBookListFragment;
import com.codepath.android.booksearch.fragments.BookSearchFragment;
import com.codepath.android.booksearch.models.Book;
import android.support.v7.widget.SearchView;
import com.codepath.android.booksearch.net.BookClient;
import com.codepath.android.booksearch.net.JsonKeys;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.SerializableCookie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

import static com.codepath.android.booksearch.R.id.lvBooks;

/**
 * Created by gretel on 10/20/17.
 */

public class BookSearchActivity extends BaseActivity implements BookSearchFragment.BookListReadyListener, BookSearchFragment.BookListClickListener {

    BookSearchFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_search);
        ButterKnife.bind(this);



        fragment = new BookSearchFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment, "TAG");
        transaction.commit();

        setupToolbar();



    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        enableToolbarBackButton();
        showToolbarBackButton();
        setTitle(R.string.search_text);
    }

    @Override
    public boolean onPrepareOptionsMenu(final Menu menu){
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return super.onCreateOptionsMenu(menu);
    }




//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_search, menu);
//        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
//
//        final SearchView searchView = (SearchView) searchMenuItem.getActionView();
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//
//        if(null!=searchManager){
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        }
//
//        searchView.setIconifiedByDefault(false);
//
//
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//
//
//                searchView.clearFocus();
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
//        return super.onCreateOptionsMenu(menu);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.action_search){
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClickListener(Book book) {

    }

    @Override
    public void onReadyListener(BaseBookListFragment.FragmentType type) {

    }
}
