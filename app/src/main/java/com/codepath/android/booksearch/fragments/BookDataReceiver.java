package com.codepath.android.booksearch.fragments;

import com.codepath.android.booksearch.models.Book;

import java.util.List;

/**
 * Created by gretel on 10/19/17.
 */

public interface BookDataReceiver {
    void pushData(List<Book> books);
}
