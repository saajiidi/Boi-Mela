package com.example.e_boimela;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;



public class ViewObserversActivity extends AppCompatActivity {

    private ListView listViewObserversList;
    private List<RegisterObservers> registerObserversAdapter = new ArrayList<>();
    private DatabaseReference databaseReference;
    private String get_current_stall_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_observers);

        listViewObserversList = findViewById(R.id.listViewObservers);


        Intent intent = getIntent();
        get_current_stall_name = intent.getExtras().getString("current_stall_name");



        databaseReference = FirebaseDatabase.getInstance().getReference("Observers");

        Query query = FirebaseDatabase.getInstance().getReference("Observers").orderByChild("observerStallName").equalTo(get_current_stall_name);
        query.addListenerForSingleValueEvent(valueEventListener);

    }



    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            registerObserversAdapter.clear();

            for (DataSnapshot bookInfoSnapshot : dataSnapshot.getChildren()) {

                RegisterObservers r = bookInfoSnapshot.getValue(RegisterObservers.class);



            }

            RegisterObserverList registerObserverList = new RegisterObserverList(ViewObserversActivity.this, registerObserversAdapter);
            listViewObserversList.setAdapter(registerObserverList);
            registerObserversAdapter.add(r);



        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }


    };

}














