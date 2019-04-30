package com.example.e_boimela;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SetNotificationOnActivity extends AppCompatActivity {

    private FloatingActionButton fab;

    private String replaced_char_email, replaced_char_email_final, get_current_user_email,get_clicked_stallName, get_clicked_stallNumber, show_clicked_stallName, show_clicked_stallNumber;

    private ListView listViewStallDetails;

    private List<StuffStallDetails> stallDetailsList;

    private DatabaseReference databaseBookDetails;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification_on);


        databaseBookDetails = FirebaseDatabase.getInstance().getReference("Users");

        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("emailAddress");
        query.addListenerForSingleValueEvent(valueEventListener);

        //quering for all
        //   databaseBookDetails.addListenerForSingleValueEvent(valueEventListener);




        listViewStallDetails = findViewById(R.id.listViewStallNames);

        stallDetailsList = new ArrayList<>();


        listViewStallDetails.setClickable(true);
        listViewStallDetails.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                get_clicked_stallName =((TextView)arg1.findViewById(R.id.textViewListStallName)).getText().toString();
                get_clicked_stallNumber =((TextView)arg1.findViewById(R.id.textViewListStallNumber)).getText().toString();

                String[] words = get_clicked_stallName.split(":");
                String[] words2 = get_clicked_stallNumber.split(":");
                show_clicked_stallName = (words[words.length-1].trim());
                show_clicked_stallNumber = (words2[words2.length-1].trim());

                AlertDialog.Builder builder = new AlertDialog.Builder(SetNotificationOnActivity.this);
                builder.setTitle("Notification");
                builder.setIcon(getResources().getDrawable(R.drawable.get_notification));
                builder.setMessage("Do you want to get Notifications from this stall?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(SetNotificationOnActivity.this, UserInformationActivity.class);
                        i.putExtra("code_stall_name",show_clicked_stallName);
                        i.putExtra("code_stall_number",show_clicked_stallNumber);
                        startActivity(i);

                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


            }
        });


    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            stallDetailsList.clear();

            for (DataSnapshot bookInfoSnapshot : dataSnapshot.getChildren()){
                StuffStallDetails stuffStallDetails = bookInfoSnapshot.getValue(StuffStallDetails.class);

                stallDetailsList.add(stuffStallDetails);

            }
            StallNameDetailsList adapter = new StallNameDetailsList(SetNotificationOnActivity.this, stallDetailsList);
            listViewStallDetails.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    @Override
    protected void onStart() {

        super.onStart();
    }





}
