package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    RecyclerView mainRecyclerView;

    String[] items, descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mainRecyclerView = findViewById(R.id.mainRecyclerView);

        items = getResources().getStringArray(R.array.items);
        descriptions = getResources().getStringArray(R.array.descriptions);


        MainRecyclerViewAdapter mainAdapter = new MainRecyclerViewAdapter(this, items, descriptions);
        mainRecyclerView.setAdapter(mainAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}