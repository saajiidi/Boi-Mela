package com.example.e_boimela;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextBookName, editTextAuthorName, editTextBookPrice;
    private String  stall_name, stall_number, book_name, author_name, book_price;
    private CardView cardViewButton;
    private String get_current_user_email, get_current_stall_name, get_current_stall_number, get_listed_email_address, get_observers;


    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;

    DatabaseReference databaseBookDetails, databaseObservers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_information);

        Intent intent = getIntent();
        get_current_user_email = intent.getExtras().getString("current_email");
        get_current_stall_name = intent.getExtras().getString("current_stall_name");
        get_current_stall_number = intent.getExtras().getString("current_stall_number");
      //  get_listed_email_address = intent.getExtras().getString("code_listed_user_email");



        databaseBookDetails = FirebaseDatabase.getInstance().getReference("Users");
        databaseObservers = FirebaseDatabase.getInstance().getReference("Observers");

        mContext = getApplicationContext();
        mActivity = AddInformationActivity.this;
        mRelativeLayout =  findViewById(R.id.rl);


        editTextBookName     =  findViewById(R.id.editTextBookName);
        editTextAuthorName   =  findViewById(R.id.editTextAuthorName);
        editTextBookPrice    =  findViewById(R.id.editTextBookPrice);

        cardViewButton = findViewById(R.id.CardViewAdd);

        cardViewButton.setOnClickListener(this);

        databaseObservers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot chidSnap : dataSnapshot.getChildren()) {

                    get_observers = (String) chidSnap.child("observerEmailAddress").getValue();

                    Log.v("observers", "" + get_observers);
                    Toast.makeText(getApplicationContext(),get_observers,Toast.LENGTH_LONG).show();


                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    public void addInfo()
    {

        stall_name = get_current_stall_name;
        stall_number = get_current_stall_number;
        book_name = editTextBookName.getText().toString().trim();
        author_name = editTextAuthorName.getText().toString().trim();
        book_price = editTextBookPrice.getText().toString().trim();


        if ((book_name.isEmpty())){
        editTextBookName.setError("Book Name is Empty!");

        } else if ((author_name.isEmpty())){
        editTextAuthorName.setError("Author Name is Empty!");

        } else if ((book_price.isEmpty())){
        editTextBookPrice.setError("Book Price is Empty!");

    } else {

            String id = databaseBookDetails.push().getKey();
            BookDetails bookDetails = new BookDetails(get_current_user_email,id,stall_name,stall_number,book_name,author_name,book_price);
            databaseBookDetails.child(id).setValue(bookDetails);
            Toast.makeText(this, "Info added successfully!", Toast.LENGTH_LONG).show();
            editTextReset();
        }

    }

    private void editTextReset() {

        editTextBookName.setText("");
        editTextAuthorName.setText("");
        editTextBookPrice.setText("");

    }

    private  void showProgressDialog() {

        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(AddInformationActivity.this);
        progressDialog.setIcon(R.drawable.buffering);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
         progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AddInformationActivity.this, StaffHomeActivity.class);
                intent.putExtra("code_current_gmail",   get_current_user_email);
                intent.putExtra("code_stall_name",      get_current_stall_name);
                intent.putExtra("code_stall_number",    get_current_stall_number);

                startActivity(intent);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    @Override
    public void onClick(View view) {
      //   addInfo();
         notifyObservers();
    }

    private void notifyObservers() {
        String[] emails = new String[]{
                "shihababrar99@gmail.com"
        };

        // emails for cc
        /*String[] emailsCC = new String[]{
                "jim@example.com"
        };*/

        // emails for bcc
       /* String[] emailsBCC = new String[]{
                "tom@example.com"
        };*/

        String mailSubject = "This is email subject";
        String mailBody = "This is the body of the email";


        // Initialize a new Intent
        Intent intent = new Intent(Intent.ACTION_SENDTO);


        intent.putExtra(Intent.EXTRA_EMAIL,emails);


        // Define the email cc and bcc
     //   intent.putExtra(Intent.EXTRA_CC,emailsCC);
     //   intent.putExtra(Intent.EXTRA_BCC,emailsBCC);

        intent.putExtra(Intent.EXTRA_SUBJECT,mailSubject);

        intent.putExtra(Intent.EXTRA_TEXT,mailBody);


        // For only email app should handle this intent
        intent.setData(Uri.parse("mailto:"));

        // Try to start the activity
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }else{
            // If there are no email client installed in this device
            Toast.makeText(mContext,"No email client installed in this device.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        showProgressDialog();

    }


}
