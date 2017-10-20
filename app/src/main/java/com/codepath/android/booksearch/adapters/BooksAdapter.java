package com.codepath.android.booksearch.adapters;

import android.content.Context;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.android.booksearch.R;
import com.codepath.android.booksearch.activities.BookDetailActivity;
import com.codepath.android.booksearch.fragments.BookDetailFragment;
import com.codepath.android.booksearch.models.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.codepath.android.booksearch.R.id.ivBookCover;
import static com.codepath.android.booksearch.R.id.tvAuthor;
import static com.codepath.android.booksearch.R.id.tvTitle;
import static java.security.AccessController.getContext;

/**
 * Created by gretel on 10/19/17.
 */

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.ViewHolder> {

    private ArrayList<Book> books;
    private Context context;

    BookClickListener listener;

    int rowSelectedIndex = -1;

    public interface BookClickListener{
        void onBookClickListener(Book book);
    }

    public void setListener(BookClickListener listener){
        this.listener = listener;

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llBook;
        TextView tvTitle;
        TextView tvAuthor;
        ImageView ivCover;

        public ViewHolder(View itemView){
            super(itemView);

            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            ivCover = (ImageView) itemView.findViewById(ivBookCover);
        }

        public void bind(Book book){
        tvTitle.setText(book.getTitle());
        tvAuthor.setText(book.getAuthor());
       // Picasso.with(getContext().load(Uri.parse(book.getCoverUrl())).placeholder(R.drawable.ic_nocover).into(ivCover);
        }
    }

    public BooksAdapter(Context context, ArrayList<Book> books){
        this.context = context;
        this.books = books;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View bookView = inflater.inflate(R.layout.item_book, parent, false);

        return new ViewHolder(bookView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        final Book book = books.get(position);
        holder.bind(book);

        holder.llBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rowSelectedIndex = position;
                listener.onBookClickListener(book);
                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                BookDetailFragment dialog = BookDetailFragment.newInstance(book);
                dialog.show(manager, "Dialog");

                notifyDataSetChanged();
            }
        });

        if(rowSelectedIndex == position){
            holder.llBook.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
            holder.tvTitle.setTextColor(ContextCompat.getColor(context, R.color.white));
        }


    }

    @Override
    public int getItemCount(){ return books.size();}




}
