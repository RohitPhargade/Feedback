package com.example.feedback;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.SweepGradient;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.ColorStateListDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedback.databinding.ActivityLoginactivityBinding;
//import com.google.android.gms.common.api.internal.IStatusCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
//import com.google.android.material.appbar.AppBarLayout;
//import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseAuthSettings;
import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import org.w3c.dom.Text;

//import cn.pedant.SweetAlert.SweetAlertDialog;


public class loginactivity extends AppCompatActivity {

//        private Button login ;
//       private EditText id, pass;


    private ActivityLoginactivityBinding binding;
    private FirebaseAuth mAuth;                 /////////////////////////
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;


    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
//        EditText e1 = findViewById(R.id.btnmail);
//        EditText e2 = findViewById(R.id.btnpass);
//        e1.setText("");
//        e2.setText("");
//        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
//
//        WindowInsetsControllerCompat windowInsetsController =
//                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
//        if (windowInsetsController == null) {
//            return;
//        }
//windowInsetsController.setAppearanceLightStatusBars(true);
//        windowInsetsController.setAppearanceLightNavigationBars(true);

        // If the Android version is lower than Jellybean, use this call to hide
        // the status bar.
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);


        binding = ActivityLoginactivityBinding.inflate(getLayoutInflater());


//            binding.btnmail.getText().toString() , binding.btnpass.getText().toString() ,

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());

//        getSupportActionBar().setTitle("Owner Login");

        binding.btnmail.setText("");
        binding.btnpass.setText("");

        progressDialog = new ProgressDialog(loginactivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");


        binding.btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.btnmail.getText().toString().length() != 0 && binding.btnpass.getText().toString().length() != 0) {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.btnmail.getText().toString(), binding.btnpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                saveLastButtonPressed(binding.btnmail.getText().toString(), binding.btnpass.getText().toString());
                                Intent intent = new Intent(loginactivity.this, ShowActivity.class);
                                binding.btnmail.setText("");
                                binding.btnpass.setText("");
                                startActivity(intent);
                            } else {
                                Toast.makeText(loginactivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    if (binding.btnmail.getText().toString().length() == 0 && binding.btnpass.getText().toString().length() == 0) {
                        binding.btnmail.setError("Please Enter Your Email");
                        binding.btnpass.setError("Please Enter Your Pass");
                        Toast.makeText(loginactivity.this, "Please Enter Value", Toast.LENGTH_SHORT).show();

                    } else if (binding.btnmail.getText().toString().length() == 0) {
                        binding.btnmail.setError("Please Enter Your Email");

                    } else {
                        binding.btnpass.setError("Please Enter Your Pass");
                    }
                }
            }
        });





        TextView txt2 = (TextView) findViewById(R.id.cust_fail_dialog_msg);

        binding.forgotPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                GradientDrawable gd = new GradientDrawable();
                gd.setStroke(5, Color.parseColor("#00A0EA"));
                gd.setCornerRadius(15);
                gd.setSize(30, 30);
                resetMail.setBackground(gd);
                AlertDialog.Builder resetpassword = new AlertDialog.Builder(v.getContext());
                resetpassword.setTitle("Reset Password ?");
                resetpassword.setMessage("Enter Your Registered Email To Received Reset Link");
                resetpassword.setView(resetMail);

                resetpassword.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        if (!mail.isEmpty()) {
                            String finalMail = mail;
                            mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {


                                    try {
                                        Dialog dialog;
//                                    //Create the Dialog here
                                        dialog = new Dialog(loginactivity.this);


                                        dialog.setContentView(R.layout.cust_dialog_reset_succ);

//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.ui3));
                                        }

                                        TextView textView = dialog.findViewById(R.id.txt_dialog_mesg);
                                        TextView btn_okk = dialog.findViewById(R.id.btn_ok_suc);

                                        textView.setText("Reset Email Is Sent To "+finalMail);
                                        btn_okk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        dialog.show();
                                    } catch (Exception x) {
                                        Toast.makeText(loginactivity.this, "Hi " + x, Toast.LENGTH_SHORT).show();
                                    }




//                                    AlertDialog alertDialog = new AlertDialog.Builder(loginactivity.this).create();
//                                    LayoutInflater inflater = getLayoutInflater();
//                                    alertDialog.setView(inflater.inflate(R.layout.cust_dialog_reset_succ, null));
//
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//                                                    TextView tv = findViewById(R.id.txt_dialog_mesg);
//                                                    tv.setText("Reset Email Is Sent To " + finalMail);
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    alertDialog.show();

//                                    Toast.makeText(loginactivity.this, "Reset Link Sent To Your Email", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @SuppressLint("UseCompatLoadingForDrawables")
                                @Override
                                public void onFailure(@NonNull Exception e) {


//                                    SweetAlertDialog pDialog = new SweetAlertDialog(loginactivity.this, SweetAlertDialog.PROGRESS_TYPE);
//                                    pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
//                                    pDialog.setTitleText("Loading");
//                                    pDialog.setCancelable(false);
//                                    pDialog.show();

                                    try {
                                        Dialog dialog;
//                                    //Create the Dialog here
                                        dialog = new Dialog(loginactivity.this);


                                        dialog.setContentView(R.layout.cust_dialog_reset_fail);

//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GREEN));

                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.ui3));
                                        }

                                        TextView textView = dialog.findViewById(R.id.cust_fail_dialog_msg);
                                        TextView btn_okk = dialog.findViewById(R.id.btn_ok);

                                        textView.setText("" + e.getMessage());
                                        btn_okk.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.dismiss();
                                            }
                                        });

                                        dialog.show();
                                    } catch (Exception x) {
                                        Toast.makeText(loginactivity.this, "Hi" + x, Toast.LENGTH_SHORT).show();
                                    }
//                                    }
//                                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                                    dialog.setCancelable(false); //Optional
//                                   // dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation; //Setting the animations to dialog


//                                    AlertDialog alertDialog = new AlertDialog.Builder(loginactivity.this).create();
//                                    LayoutInflater inflater = getLayoutInflater();
//                                    alertDialog.setView(inflater.inflate(R.layout.cust_dialog_reset_fail, null));
//                                    txt2.setText("Fail ");
////                                    TextView tv2 = findViewById(R.id.cust_fail_dialog_msg);
////                                    tv2.setText("Fail " + e.getMessage());
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
//                                            new DialogInterface.OnClickListener() {
//                                                public void onClick(DialogInterface dialog, int which) {
//
//                                                    dialog.dismiss();
//                                                }
//                                            });
//                                    alertDialog.show();
                                    Toast.makeText(loginactivity.this, "Error ! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        mail = null;


                    }
                });
                resetpassword.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                resetpassword.create().show();
            }
        });


        binding.offdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
                String loginname = sharedPref.getString("lastemail", "");
                if (loginname != null && !loginname.equals("")) {
                    progressDialog.dismiss();
                    binding.btnmail.setText("");
                    binding.btnpass.setText("");
                    Intent intent = new Intent(loginactivity.this, showofflinedata.class);
                    startActivity(intent);


                } else {
                    progressDialog.dismiss();
                    binding.btnmail.setText("");
                    binding.btnpass.setText("");
                    Toast.makeText(loginactivity.this, "Please Sign In First", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(loginactivity.this, SignInActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    public void onBackPressed() {
        Intent intent = new Intent(loginactivity.this, MainOptionPage.class);
        mAuth.signOut();
        binding.btnmail.setText("");
        binding.btnpass.setText("");
        startActivity(intent);
        super.onBackPressed();

    }
    public void saveLastButtonPressed(String uemail, String upassword) {
//        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
//        SharedPreferences.Editor Udetails = sharedPref.edit();
//        Udetails.putString("lastemail", uemail);
//        Udetails.putString("lastpassword", upassword);
//        Toast.makeText(loginactivity.this,uemail+" "+ upassword + "saved", Toast.LENGTH_SHORT).show();
//        Udetails.apply();
    }


}