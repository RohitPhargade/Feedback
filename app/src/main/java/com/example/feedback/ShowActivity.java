package com.example.feedback;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FirebaseDatabase db = FirebaseDatabase.getInstance();
    private DatabaseReference root;
    private MyAdapter adapter;
    private FirebaseAuth mAuth;
    private ArrayList<Model> list;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show);


        Window window = getWindow();
        window.setStatusBarColor(R.drawable.ui_list_bg);
        getWindow().setBackgroundDrawableResource(R.drawable.ui_list_bg);


        SharedPreferences sp1 = getSharedPreferences("application", Context.MODE_PRIVATE);
        String temp = sp1.getString("lastemail", "");
        String childname= temp.replace("@gmail.com","");

        root  = db.getReference().child(childname);



        mAuth = FirebaseAuth.getInstance();


        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();

        adapter = new MyAdapter(this, list);

        recyclerView.setAdapter(adapter);


//        public void saveLastButtonPressed(String uemail, String upassword) {
//            SharedPreferences sharedPref = getSharedPreferences("application", Context.MODE_PRIVATE);
//            SharedPreferences.Editor Udetails = sharedPref.edit();
//            Udetails.putString("lastemail", uemail);
//            Udetails.putString("lastpassword", upassword);
//            Toast.makeText(loginactivity.this,uemail+" "+ upassword + "saved", Toast.LENGTH_SHORT).show();
//            Udetails.apply();
//        }


//        root.addChildEventListener()

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Model model = dataSnapshot.getValue(Model.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void onBackPressed() {

        Intent intent = new Intent(ShowActivity.this, loginactivity.class);
        mAuth.signOut();
        startActivity(intent);
        super.onBackPressed();

    }

}