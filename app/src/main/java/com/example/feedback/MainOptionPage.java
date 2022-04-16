package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedback.databinding.ActivityMainOptionPageBinding;

public class MainOptionPage extends AppCompatActivity {

//    TextView btnform, btncheck, btnlang, btnsignin, btnCreateAcc;

    private ActivityMainOptionPageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainOptionPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        String loginname = getsharedDetails();
        if (loginname != null && !loginname.equals("")) {

            binding.loginn.setText(loginname.replace("@gmail.com","").toUpperCase());
            binding.signinoptionpage.setVisibility(View.GONE);
            binding.signout.setVisibility(View.VISIBLE);
        }
        else {
            binding.welcomemsg.setText("Please Log In First");
            binding.welcomemsg.setTextColor(Color.RED);
            binding.loginn.setVisibility(View.GONE);
            binding.signout.setVisibility(View.GONE);
            binding.signinoptionpage.setVisibility(View.VISIBLE);
        }

//        btnform = findViewById(R.id.form);
//        btncheck = findViewById(R.id.checkdata);
//        btnlang = findViewById(R.id.language);
//        btnsignin = findViewById(R.id.signin);
//        btnCreateAcc = findViewById(R.id.CreateAcc);

        binding.form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.signinoptionpage.getVisibility() == View.GONE) {
                    Intent intent = new Intent(MainOptionPage.this, FormActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(MainOptionPage.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
                }
                
            }
        });
        binding.checkdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptionPage.this, loginactivity.class);
                startActivity(intent);
            }
        });
        binding.language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptionPage.this, LanguageActivity.class);
                startActivity(intent);
            }
        });
        binding.signinoptionpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signinoptionpage = new Intent(MainOptionPage.this, SignInActivity.class);
                startActivity(signinoptionpage);
//                Intent refresh = new Intent(MainOptionPage.this, MainOptionPage.class);
//                startActivity(refresh);//Start the same Activity
                finish();
            }
        });
        binding.CreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainOptionPage.this, CreateAccount.class);
                startActivity(intent);

            }
        });
        binding.signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setSharedDetails("","");
                Intent refresh = new Intent(MainOptionPage.this, MainOptionPage.class);
                startActivity(refresh);//Start the same Activity
                finish();
            }
        });


    }

    public String getsharedDetails() {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor Udetails = sharedPref.edit();
        String l = sharedPref.getString("lastemail", "");
        sharedPref.getString("lastpassword", "");
        Toast.makeText(MainOptionPage.this, l + " get", Toast.LENGTH_SHORT).show();
        Udetails.apply();
        return l;
    }
    public void setSharedDetails(String uemail, String upassword) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor Udetails = sharedPref.edit();
        Udetails.putString("lastemail", uemail);
        Udetails.putString("lastpassword", upassword);
        Toast.makeText(MainOptionPage.this,uemail+" "+ upassword + "saved", Toast.LENGTH_SHORT).show();
        Udetails.apply();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}