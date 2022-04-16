package com.example.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.feedback.LocaleHelper;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LanguageActivity extends AppCompatActivity {

    RadioGroup radioGroupbtn;
    RadioButton radiobtn1, radiobtn2, radiobtn3;
    Button btnset;

    TextView messageView;
    Button btnHindi, btnEnglish;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        getSupportActionBar().hide();
        radioGroupbtn = findViewById(R.id.radiogroup2);
        radiobtn1 = findViewById(R.id.radioButton1);
        radiobtn2 = findViewById(R.id.radioButton2);
        radiobtn3 = findViewById(R.id.radioButton3);
        btnset = findViewById(R.id.setlanguage);
        messageView = findViewById(R.id.msg);


//        radioGroupbtn.clearCheck();

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int id = radioGroupbtn.getCheckedRadioButtonId();
                RadioButton rb = findViewById(id);
                String selectedLang = rb.getText().toString();
                if (rb != null) {
                    if (selectedLang.equals("English")) {
                        Toast.makeText(LanguageActivity.this, "English Language is Selected", Toast.LENGTH_SHORT).show();
                        context = LocaleHelper.setLocale(LanguageActivity.this, "en");
                        resources = context.getResources();
                        messageView.setText(resources.getString(R.string.normaltext));


                    } else if (rb.getText().toString().equals("Hindi")) {
                        Toast.makeText(LanguageActivity.this, "Hindi Language is Selected", Toast.LENGTH_SHORT).show();
                        context = LocaleHelper.setLocale(LanguageActivity.this, "hi-rIN");
                        resources = context.getResources();
                        messageView.setText(resources.getString(R.string.normaltext));
                    } else {
                        Toast.makeText(LanguageActivity.this, selectedLang +" Marathi Language is Selected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LanguageActivity.this, "Please Select Your Language First Then Click On Set Button", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}