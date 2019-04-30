package com.example.e_boimela;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetNotificationOffActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editText_emailAddressRemoveObserver;
    private Button button_sendRemoveObserver;
    private String currentObserverEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_notification_off);

        editText_emailAddressRemoveObserver = findViewById(R.id.idEditTextEmailAddressRemoveObserver);
        button_sendRemoveObserver = findViewById(R.id.idButtonRemoveObserver);

        button_sendRemoveObserver.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if (v == button_sendRemoveObserver){
            currentObserverEmailAddress = editText_emailAddressRemoveObserver.getText().toString();
            Intent intent = new Intent(SetNotificationOffActivity.this, ObserversEnlistedStallsActivity.class);
            intent.putExtra("code_currentObserverEmailAddress", currentObserverEmailAddress);
            startActivity(intent);

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        editText_emailAddressRemoveObserver.setText("");
    }
}
