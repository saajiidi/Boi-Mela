package com.example.boimela;
 
import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
 
import java.util.List;
 

public class BookList extends ArrayAdapter<BookInfo> {
    private Activity context;
    List<BookInfo> books;
 
    public BookList(Activity context, List<BookInfo> books) {
        super(context, R.layout.layout_book_list, books);
        this.context = context;
        this.books = books;
    }
 
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_book_list, null, true);
 
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewName);
	TextView textViewAuthor = (TextView) listViewItem.findViewById(R.id.textViewAuthor);
	TextView textViewCategory = (TextView) listViewItem.findViewById(R.id.textViewCategory);
        TextView textViewStall = (TextView) listViewItem.findViewById(R.id.textViewStall);
        TextView textViewPrice = (TextView) listViewItem.findViewById(R.id.textViewPrice);
 
        BookInfo book = books.get(position);

        textViewName.setText(book.getBookName());
	textViewAuthor.setText(book.getAuthor());
	textViewCategory.setText(book.getCategory());
        textViewStall.setText(book.getStall());
	textViewPrice.setText(book.getPrice());
 
        return listViewItem;
    }
}
