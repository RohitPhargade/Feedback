package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static com.example.feedback.SplashScreen.getConnectivityStatusString;

public class NoInternetActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);
        getSupportActionBar().hide();
        getConnectivityStatusString(NoInternetActivity.this);
    }

    public void refreshbtn(View view) {
        getConnectivityStatusString(NoInternetActivity.this);
    }

    public void getConnectivityStatusString(Context context) {

        if (SplashScreen.getConnectivityStatusString(NoInternetActivity.this)) {
            Intent intent = new Intent(NoInternetActivity.this, MainOptionPage.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "No internet is available", Toast.LENGTH_SHORT).show();
        }

    }
}