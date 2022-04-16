package com.example.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class FormActivity extends AppCompatActivity {
    TextView btnsubmit;
    EditText btnphone, btnsuggest, btnname;
    Button btnreview;
    String num;
    String suggestion;
    RadioButton excellent1, good1, average1, poor1, worst1;
    RadioGroup radioGroup1;
    ImageView imageView;


    public DatabaseHelper myDBa;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root;
    public static String dbname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sp = getSharedPreferences("application", Context.MODE_PRIVATE);
        String temp = sp.getString("lastemail", "");
        String childname = temp.replace("@gmail.com", "");
        dbname = childname;


        root = db.getReference().child(childname);

//        sp.getString("lastpassword", "");

        getSupportActionBar().hide();

        myDBa = new DatabaseHelper(FormActivity.this);

        btnsubmit = findViewById(R.id.submit);
        btnphone = findViewById(R.id.number1);
        btnsuggest = findViewById(R.id.anysuggestion);
        btnname = findViewById(R.id.name);

        excellent1 = findViewById(R.id.excellentid);
        good1 = findViewById(R.id.googid);
        average1 = findViewById(R.id.averageid);
        poor1 = findViewById(R.id.poorid);
        worst1 = findViewById(R.id.worstid);

        radioGroup1 = findViewById(R.id.radiogroup);

        imageView = findViewById(R.id.imageView2);


//        ImageView imageView = (ImageView) findViewById(R.id.imageView2);
//        imageView.setOnLongClickListener(new View.OnLongClickListener() {
//
//            @Override
//            public boolean onLongClick(View v) {
//                Intent intent = new Intent(FormActivity.this, loginactivity.class);
//                startActivity(intent);
//                return true;
//            }
//        });


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = radioGroup1.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(i);

                try {

                    if (btnphone.getText().toString().length() == 10 && radioButton.isChecked() && btnname.getText().toString().length() != 0) {
                        String name = btnname.getText().toString();
                        String number = btnphone.getText().toString();
                        String sugg = btnsuggest.getText().toString();
                        String radio = radioButton.getText().toString();


                        boolean isInserted = myDBa.insertData(btnname.getText().toString() + "", btnphone.getText().toString() + "", radio + "", btnsuggest.getText().toString() + "");

                        if (isInserted) {
                            Toast.makeText(FormActivity.this, "Data Inserted...", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(FormActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();


                        HashMap<String, String> userMap = new HashMap<>();
                        userMap.put("name", name);
                        userMap.put("number", number);
                        userMap.put("suggestions", sugg);
                        userMap.put("radio", radio);
                        root.push().setValue(userMap);

                        AlertDialog alertDialog = new AlertDialog.Builder(FormActivity.this).create();
                        LayoutInflater inflater = getLayoutInflater();
                        alertDialog.setView(inflater.inflate(R.layout.mycustomdialog, null));
//                    alertDialog.setTitle("Alert");
//                    alertDialog.setMessage("Alert message to be shown");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        btnphone.setText("");
                                        btnsuggest.setText("");
                                        radioGroup1.clearCheck();
                                        btnname.setText("");

                                    }
                                });
                        alertDialog.show();

//                try {
//                    Thread.sleep(2000);
//                    alertDialog.dismiss();
//                }catch (Exception e)
//                {
//                    Toast.makeText(MainActivity.this, "cant sleep", Toast.LENGTH_SHORT).show();
//                }
                    } else {
                        if (btnphone.getText().toString().length() == 10) {

                            btnname.setError("Please Enter Your Name");
                        } else {
                            btnphone.setError("Please Enter Phone Number");
                            Toast.makeText(FormActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {

                    Toast.makeText(FormActivity.this, "Please Select the Radio Button", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.login:
                Intent intent = new Intent(FormActivity.this, loginactivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


//    public void onBackPressed() {

//        new AlertDialog.Builder(FormActivity.this)
//                .setIcon(R.drawable.ic_baseline_exit_to_app_24)
//                .setTitle("Exit")
//                .setMessage("Do You Want to Exit")
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        moveTaskToBack(true);
//                        finish();
//                    }
//                })
//
//                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).show();


//                        finish();
//                        finish();

//                        Intent intent = new Intent(Intent.ACTION_MAIN);
//      //                  intent.addCategory(Intent.CATEGORY_HOME);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(intent);
//                        System.exit(1);

    // android.os.Process.killProcess(android.os.Process.myPid());

//                .setNeutralButton("Help", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(MainActivity.this, "We will call you back", Toast.LENGTH_SHORT).show();
//            }
//        })


//    }


}