package com.example.boimela;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText editText_emailaddress, editText_password;
    private CardView cardView_login;
    private TextView  textView_forgot_password;
    private String user_email_address, user_password, emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private Boolean flag = false;
    private SignInButton googleSignInButton;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;
    private  GoogleSignInAccount googleSignInAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText_emailaddress = findViewById(R.id.editTextEmail);

        editText_password = findViewById(R.id.editTextPassword);

        cardView_login = findViewById(R.id.CardViewLogIn);
        googleSignInButton = findViewById(R.id.sign_in_button);

        textView_forgot_password = findViewById(R.id.textViewForgotPassword);


        textView_forgot_password.setOnClickListener(this);

        cardView_login.setOnClickListener(this);
        googleSignInButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
        user_email_address = editText_emailaddress.getText().toString().trim();
        user_password = editText_password.getText().toString().trim();
        if(view == cardView_login) {
            if(user_email_address.isEmpty())
            {
                editText_emailaddress.setError("E-mail Address is empty!");
                flag = true;
            } else if(!(user_email_address.matches(emailPattern)))
            {
                editText_emailaddress.setError("Only valid E-mail Address!");
                flag = true;
            }  else if(user_password.isEmpty())
            {
                editText_password.setError("Password Field is empty!");
            }  else if(user_password.length() > 18)
            {
                editText_password.setError("Password is too large!");
            }

            else if (checkNetworkConnection(MainActivity.this)==false)
            {
                noInternetAlert();

            }
            else {
                signIn(user_email_address,user_password);
                // Toast.makeText(getApplicationContext(), "Good2Go", Toast.LENGTH_SHORT).show();

            }
        }
        else if(view == googleSignInButton){
                signInGoogle();

        }

        else if(view == textView_forgot_password)
        {
            if( !(user_email_address.isEmpty())) {
                resetPassword(user_email_address);
            }
        }
        else if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        
    }

    private void signInGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }


    private void signIn(String email, String password) {
        //  Log.e(TAG, "signIn:" + email);
        if (!validateForm(email, password)) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login Success: ", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, StuffStallActivity.class);
                                intent.putExtra("set_current_user_email", user_email_address);
                                startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed!", Toast.LENGTH_SHORT).show();
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "User is not registered!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private boolean validateForm(String email, String password) {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Intent intent = new Intent(this, StuffStallActivity.class);
        intent.putExtra("set_current_user_email",googleSignInAccount.getEmail());
        Toast.makeText(getApplicationContext(), "Current User: "+googleSignInAccount.getEmail(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case 101:
                    try {
                        // The Task returned from this call is always completed, no need to attach
                        // a listener.
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        onLoggedIn(account);
                    } catch (ApiException e) {
                        // The ApiException status code indicates the detailed failure reason.
                       // Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
                    }
                    break;
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        editText_emailaddress.setText("");
        editText_password.setText("");
        googleSignInClient.signOut();
    }



    private void resetPassword(String email) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Link Sent to reset password", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    private boolean checkNetworkConnection(Context context)
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo)
        {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
            {
                if (ni.isConnected())
                {
                    haveConnectedWifi = true;
                    Log.v("WIFI CONNECTION ", "AVAILABLE");
                } else
                {
                    Log.v("WIFI CONNECTION ", "NOT AVAILABLE");
                }
            }
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
            {
                if (ni.isConnected())
                {
                    haveConnectedMobile = true;
                    Log.v("INTERNET CONNECTION ", "AVAILABLE");
                } else
                {
                    Log.v("INTERNET CONNECTION ", "NOT AVAILABLE");
                }
            }
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    private void noInternetAlert()
    {
        android.support.v7.app.AlertDialog.Builder noIntBuilder = new android.support.v7.app.AlertDialog.Builder(MainActivity.this);
        noIntBuilder.setTitle(" No Internet");
        noIntBuilder.setIcon(getResources().getDrawable(R.drawable.no_network) );
        noIntBuilder.setMessage("  Turn On Internet Connection");
        noIntBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        android.support.v7.app.AlertDialog alert = noIntBuilder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(MainActivity.this, SeparateUserActivity.class));
        finish();
    }


}