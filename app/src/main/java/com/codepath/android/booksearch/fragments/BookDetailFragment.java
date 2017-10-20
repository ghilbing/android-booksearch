package com.codepath.android.booksearch.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.models.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by gretel on 10/19/17.
 */

public class BookDetailFragment extends DialogFragment {

    static final String TAG = BookDetailFragment.class.getSimpleName();
    public static final String EXTRA_BOOK = "book";


    Book mBook;

    private ImageView ivBookCover;
    private TextView tvTitle;
    private TextView tvAuthor;
    private TextView tvPublisher;
    // private TextView tvEditorialReview;



    public BookDetailFragment() {
        // Required empty public constructor
    }


    public static BookDetailFragment newInstance(Book book) {
        BookDetailFragment fragment = new BookDetailFragment();
        // Bundle args = new Bundle();
        // args.putParcelable(EXTRA_BOOK, Parcels.wrap(book));
        // fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBook = getArguments().getParcelable(EXTRA_BOOK);
            Log.i("Book", String.valueOf(mBook.getTitle()));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  mBook = DataTest.getFakeBook().get(0);


        Bundle bundle = this.getArguments();
        if(bundle != null){
            mBook = bundle.getParcelable(EXTRA_BOOK);
        }

        View view =  inflater.inflate(R.layout.fragment_book_detail, container, false);


        ivBookCover = (ImageView) view.findViewById(R.id.ivBookCover);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvAuthor = (TextView) view.findViewById(R.id.tvAuthor);
        tvPublisher = (TextView) view.findViewById(R.id.tvPublisher);
        // tvEditorialReview = view.findViewById(R.id.tvEditorialReview);

        getDialog().setTitle(tvTitle.getText().toString());

        if(mBook != null){
            setupView();

        }
        return view;

    }

    private void setUpApi(){

    }

    private void setupView(){
        Picasso.with(getContext())
                .load(R.drawable.ic_launcher)
                //.asBitmap()
                //.centerCrop()
                .into(ivBookCover);

        tvTitle.setText(mBook.getTitle());
        tvAuthor.setText(mBook.getAuthor());
        tvPublisher.setText(mBook.getPublisher());
        // tvEditorialReview.setText(mBook.getShortDescription());
    }



    @Override
    public void onDetach() {
        super.onDetach();

    }
}
