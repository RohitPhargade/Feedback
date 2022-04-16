package com.example.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.feedback.databinding.ActivityLoginactivityBinding;
import com.example.feedback.databinding.ActivitySignInBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {
    private ActivitySignInBinding binding;


    private FirebaseAuth mAuth;                 /////////////////////////
    private FirebaseDatabase database;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        binding.uemailSignin.setText("");
        binding.upassSignin.setText("");

        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");


        binding.ubtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.uemailSignin.getText().toString().length() != 0 && binding.upassSignin.getText().toString().length() != 0) {
                    progressDialog.show();
                    mAuth.signInWithEmailAndPassword(binding.uemailSignin.getText().toString(), binding.upassSignin.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if (task.isSuccessful()) {
                                saveLastButtonPressed(binding.uemailSignin.getText().toString(), binding.upassSignin.getText().toString());
                                onBackPressed();
                                binding.uemailSignin.setText("");
                                binding.upassSignin.setText("");
//                                startActivity(intent);
                            } else {
                                Toast.makeText(SignInActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    if (binding.uemailSignin.getText().toString().length() == 0 && binding.upassSignin.getText().toString().length() == 0) {
                        binding.uemailSignin.setError("Please Enter Your Email");
                        binding.upassSignin.setError("Please Enter Your Pass");
                        Toast.makeText(SignInActivity.this, "Please Enter Value", Toast.LENGTH_SHORT).show();

                    } else if (binding.uemailSignin.getText().toString().length() == 0) {
                        binding.uemailSignin.setError("Please Enter Your Email");

                    } else {
                        binding.upassSignin.setError("Please Enter Your Pass");
                    }
                }
            }
        });


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
                                        dialog = new Dialog(SignInActivity.this);
                                        dialog.setContentView(R.layout.cust_dialog_reset_succ);
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
                                        Toast.makeText(SignInActivity.this, "Hi " + x, Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @SuppressLint("UseCompatLoadingForDrawables")
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    try {
                                        Dialog dialog;
                                        dialog = new Dialog(SignInActivity.this);
                                        dialog.setContentView(R.layout.cust_dialog_reset_fail);
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
                                        Toast.makeText(SignInActivity.this, "Hi" + x, Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(SignInActivity.this, "Error ! Reset Link is not sent" + e.getMessage(), Toast.LENGTH_SHORT).show();
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




    }

    public void saveLastButtonPressed(String uemail, String upassword) {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        SharedPreferences.Editor Udetails = sharedPref.edit();
        Udetails.putString("lastemail", uemail);
        Udetails.putString("lastpassword", upassword);
        Toast.makeText(SignInActivity.this, uemail + " " + upassword + "saved", Toast.LENGTH_SHORT).show();
        Udetails.apply();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
        if (sharedPref.getString("lastemail", "") == null || sharedPref.getString("lastemail", "") == "") {
            super.onBackPressed();
        } else {
            Intent refresh = new Intent(SignInActivity.this, MainOptionPage.class);
            startActivity(refresh);//Start the same Activity
            finish();
        }

    }
}