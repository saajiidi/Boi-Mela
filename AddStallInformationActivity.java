package com.example.e_boimela;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddStallInformationActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextStallName, editTextStallNumber, editTextBookName, editTextAuthorName, editTextBookPrice;
    private String  stall_name, stall_number, book_name, author_name, book_price;
    private CardView cardViewButton;
    private String get_current_user_email;

    DatabaseReference databaseBookDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stall_information);

        Intent intent = getIntent();
        get_current_user_email = intent.getExtras().getString("current_email");
        Toast.makeText(getApplicationContext(), "Current User: "+ get_current_user_email, Toast.LENGTH_LONG).show();

        databaseBookDetails = FirebaseDatabase.getInstance().getReference("Users");

        editTextStallName    =  findViewById(R.id.editTextStallName);
        editTextStallNumber  =  findViewById(R.id.editTextStallNumber);
        editTextBookName     =  findViewById(R.id.editTextBookName);
        editTextAuthorName   =  findViewById(R.id.editTextAuthorName);
        editTextBookPrice    =  findViewById(R.id.editTextBookPrice);

        cardViewButton = findViewById(R.id.CardViewAdd);

        cardViewButton.setOnClickListener(this);



    }

    public void addInfo()
    {

        stall_name = editTextStallName.getText().toString().trim();
        stall_number = editTextStallNumber.getText().toString().trim();
        book_name = editTextBookName.getText().toString().trim();
        author_name = editTextAuthorName.getText().toString().trim();
        book_price = editTextBookPrice.getText().toString().trim();

        if ((stall_name.isEmpty())){
            editTextStallName.setError("Stall Name is Empty!");

        } else if ((stall_number.isEmpty())){
            editTextStallNumber.setError("Stall Number is Empty!");

        }
        else if ((book_name.isEmpty())){
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
        progressDialog = new ProgressDialog(AddStallInformationActivity.this);
        progressDialog.setIcon(R.drawable.buffering);
        progressDialog.setMessage("Please Wait.....");
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AddStallInformationActivity.this, StuffStallActivity.class);
                intent.putExtra("set_current_user_email",get_current_user_email);
                startActivity(intent);
                finish();
            }
        };
        Handler handler = new Handler();
        handler.postDelayed(runnable,2000);
    }

    @Override
    public void onClick(View view) {
        addInfo();
    }

    @Override
    public void onBackPressed() {

        showProgressDialog();

    }


}
