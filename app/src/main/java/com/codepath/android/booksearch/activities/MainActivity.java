package com.codepath.android.booksearch.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.fragments.BaseBookListFragment;
import com.codepath.android.booksearch.fragments.BookSearchFragment;
import com.codepath.android.booksearch.models.Book;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements BaseBookListFragment.BookListClickListener, BaseBookListFragment.BookListReadyListener{

    BookSearchFragment fragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragment = new BookSearchFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.container, fragment, "TAG");
        transaction.commit();


    }


    @Override
    public void onClickListener(Book book) {

    }


    @Override
    public void onReadyListener(BaseBookListFragment.FragmentType type) {
        fragment.pushData(new ArrayList<Book>());

    }

}
