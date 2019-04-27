package com.example.e_boimela;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imageViewSearch;

    private EditText editTextSearch;

    private String search, search_2;

    private ListView listViewBookDetails;

    public List<BookDetails> bookDetailsList;

    public DatabaseReference databaseBookDetails;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageViewSearch = findViewById(R.id.idImageViewSearch);
        imageViewSearch.setOnClickListener(this);

        editTextSearch = findViewById(R.id.idEditTextStringForSearch);

        listViewBookDetails = findViewById(R.id.idSearchlistViewBooks);


      //  databaseBookDetails = FirebaseDatabase.getInstance().getReference("Users");


        bookDetailsList = new ArrayList<>();





    }

    @Override
    public void onClick(View view) {

        search = editTextSearch.getText().toString().trim();
        if (search.isEmpty()) {
            editTextSearch.setError("Search Something!");
            bookDetailsList.clear();
        } else {

            findSearchResult(search);
        }

    }

    private void findSearchResult(String search_value) {



    }


         ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookDetailsList.clear();

                for (DataSnapshot bookInfoSnapshot : dataSnapshot.getChildren()){
                    BookDetails bookDetails = bookInfoSnapshot.getValue(BookDetails.class);

                    bookDetailsList.add(bookDetails);

                }
                BookDetailsList adapter = new BookDetailsList(SearchActivity.this, bookDetailsList);
                listViewBookDetails.setAdapter(adapter);

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };


}
