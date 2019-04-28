package com.example.e_boimela;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class BookDetailsList extends ArrayAdapter<BookDetails> {

    private TextView textViewList_stallName, textViewList_stallNumber, textViewList_bookName, textViewList_authorName, textViewList_bookPrice;
    private Activity context;
    List<BookDetails> bookDetails_adapter_list;

    public BookDetailsList(Activity context, List<BookDetails> bookDetails_adapter_list) {

        super(context, R.layout.list_layout, bookDetails_adapter_list);
        this.context = context;
        this.bookDetails_adapter_list = bookDetails_adapter_list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        textViewList_stallName =  listViewItem.findViewById(R.id.textViewListStallName);
        textViewList_stallNumber =  listViewItem.findViewById(R.id.textViewListStallNumber);
        textViewList_bookName =     listViewItem.findViewById(R.id.textViewListBookName);
        textViewList_authorName = listViewItem.findViewById(R.id.textViewListAuthorName);
        textViewList_bookPrice = listViewItem.findViewById(R.id.textViewListBookPrice);


        BookDetails bookDetails = bookDetails_adapter_list.get(position);

        textViewList_stallName.setText("Stall Name: "+bookDetails.getStallName());
        textViewList_stallNumber.setText("Stall No: "+bookDetails.getStallNumber());
        textViewList_bookName.setText("Book Name: "+bookDetails.getBookName());
        textViewList_authorName.setText("Author Name: "+bookDetails.getAuthorName());
        textViewList_bookPrice.setText("Book Price(BDT): "+bookDetails.getBookPrice());


        return listViewItem;
    }
}