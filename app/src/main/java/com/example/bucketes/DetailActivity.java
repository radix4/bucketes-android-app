//package com.example.detaileditempage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed-item);

        radioGroup = findViewById(R.id.radioGroup);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void onClick(){

    }

    public void checkButton(View v){

    }
}
