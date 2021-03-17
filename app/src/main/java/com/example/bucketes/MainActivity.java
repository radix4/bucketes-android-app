package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

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



    /**
     * This method displays the menu to the main_activity
     * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}


/** Notes:
 *  1. MainRecyclerViewAdapter is necessary to display the list to the main menu.
 *
 *
 * */