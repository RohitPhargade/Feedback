package com.example.feedback;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class showofflinedata extends AppCompatActivity {

    private DatabaseHelper myDBh;
    private TextView btnshowdata, btndeletealldata, btndeletedata;
    private EditText btniddelete;


    public final static void rohit() {
        System.out.println("hello");
    }

    public static void rohit1() {
        System.out.println("hello");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showofflinedata);

        getSupportActionBar().hide();


        myDBh = new DatabaseHelper(showofflinedata.this);
        btnshowdata = findViewById(R.id.showd);
        btniddelete = findViewById(R.id.iddelete);
        btndeletedata = findViewById(R.id.deletedata);
        btndeletealldata = findViewById(R.id.deletealldata);


        btnshowdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d();
            }
        });


        btndeletedata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = btniddelete.getText().toString();
                if (id.equals(String.valueOf(""))) {
                    btniddelete.setError("Please Enter Id");
                    return;
                }
                Integer var = myDBh.deleteData(id);

                if (var > 0) {
                    Toast.makeText(showofflinedata.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                    btniddelete.setText("");
                } else
                    Toast.makeText(showofflinedata.this, "Deletion Error", Toast.LENGTH_SHORT).show();
            }
        });
        btndeletealldata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer var = myDBh.deleteAllData();
                if (var > 0) {
                    Toast.makeText(showofflinedata.this, "All Data has been deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(showofflinedata.this, "Deletion Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void d() {

        Cursor cursor = myDBh.getAllData();
        StringBuilder buffer = new StringBuilder();
        if (cursor.getCount() == 0) {
            showMessage("Data", "Nothing found");
            return;
        }
        while (cursor.moveToNext()) {
            buffer.append("ID : ").append(cursor.getString(0)).append("\n");
            buffer.append("NAME : ").append(cursor.getString(1)).append("\n");
            buffer.append("MOBILE NUMBER : ").append(cursor.getString(2)).append("\n");
            buffer.append("RATING : ").append(cursor.getString(3)).append("\n");
            buffer.append("DESCRIPTION : ").append(cursor.getString(4)).append("\n\n");
        }
        showMessage("DATA", buffer.toString());
    }

    public void showMessage(String title, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.create();
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.show();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(showofflinedata.this, loginactivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}