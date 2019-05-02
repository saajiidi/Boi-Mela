package com.example.e_boimela;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class RegisterObserverList extends ArrayAdapter<RegisterObservers> {

    private TextView textViewObserverEmail, textViewObserverStallName;
    private Activity context;
    private List<RegisterObservers> registerObserversListAdapter;


    public RegisterObserverList(Activity context,  List<RegisterObservers> registerObserversListAdapter ) {
        super(context, R.layout.list_layout_observers, registerObserversListAdapter);
        this.context = context;
        this.registerObserversListAdapter = registerObserversListAdapter;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_observers, null, true);

        textViewObserverEmail = listViewItem.findViewById(R.id.idtextViewListObserversEmail);
        textViewObserverStallName = listViewItem.findViewById(R.id.idtextViewListObserversStallName);





        return  listViewItem;
    }
}
