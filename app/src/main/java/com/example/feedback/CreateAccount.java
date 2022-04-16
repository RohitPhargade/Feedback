package com.example.feedback;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.feedback.Models.Users;
import com.example.feedback.databinding.ActivityCreateAccountBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class CreateAccount extends AppCompatActivity {

    ActivityCreateAccountBinding binding;             //////////////////////////
    private FirebaseAuth mAuth;                 /////////////////////////
    FirebaseDatabase database;                 /////////////////////////
    ProgressDialog progressDialog;             //


    TextView signup;
    EditText n1, n2, n3, mob, passs;
//    String s1,s2,s3,s4,s5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());       ////////////////////
        mAuth = FirebaseAuth.getInstance();                                 /////////////////////

        progressDialog = new ProgressDialog(CreateAccount.this);           //
        progressDialog.setTitle("creating account");                        //
        progressDialog.setMessage("we are creating your account");          //

        setContentView(binding.getRoot());                          /////////////////xxxxx
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        getSupportActionBar().hide();
//        n1 = findViewById(R.id.firstname);
//        n2 = findViewById(R.id.midname);
//        n3 = findViewById(R.id.lastname);
//        mob = findViewById(R.id.Mobilenumber);
//        passs = findViewById(R.id.password);
//        signup = findViewById(R.id.signup1);

//        signup.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s1 = n1.getText().toString();
//                String s2 = n2.getText().toString();
//                String s3 = n3.getText().toString();
//                String s4 = mob.getText().toString();
//                String s5 = passs.getText().toString();
//                if (s1.length() != 0 && s2.length() != 0 && s3.length() != 0 && s4.length() != 0 && s5.length() != 0) {
//                    Intent intent1 = new Intent(signup.this, market1.class);
//                    startActivity(intent1);
//                } else {
//                    Toast.makeText(signup.this, "Please Enter All Details ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        mAuth = FirebaseAuth.getInstance();                  ////////////////////
        database = FirebaseDatabase.getInstance();          /////////////////////////
        binding.btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                mAuth.createUserWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            String rohit ;
                            Users user = new Users(binding.etUserName.getText().toString(), binding.etEmail.getText().toString(), binding.etPassword.getText().toString());
                            String id = task.getResult().getUser().getUid();
                            String r = binding.etEmail.getText().toString();
                            String nodename = r.replace("@gmail.com","");
                            Toast.makeText(CreateAccount.this, nodename, Toast.LENGTH_SHORT).show();
                            database.getReference().child(""+nodename).child(id).setValue(user);

                            Toast.makeText(CreateAccount.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CreateAccount.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
//        binding.tvalreadyhavecoount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(SignUpActivity.this ,market1.class);
//                startActivity(intent);
//            }
//        });

    }
}