package com.example.boimela;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class SeparateUserActivity extends AppCompatActivity implements View.OnClickListener {
    private CardView cardViewUserLogin , cardViewStaffLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_separate_user);

        cardViewUserLogin= findViewById(R.id.CardViewUserLogin);
        cardViewStaffLogIn = findViewById(R.id.CardViewStaffLogin);

        cardViewUserLogin.setOnClickListener(this);
        cardViewStaffLogIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view == cardViewUserLogin)
        {
            startActivity(new Intent(SeparateUserActivity.this, UserHomeActivity.class));
            finish();
        }
        else if (view == cardViewStaffLogIn) {
            startActivity(new Intent(SeparateUserActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SeparateUserActivity.this);
        builder.setTitle("Quit");
        builder.setIcon(getResources().getDrawable(R.drawable.ic_warning_black_24dp));
        builder.setMessage("Do you want to quit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
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
}
