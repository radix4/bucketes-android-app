package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class MainActivity extends AppCompatActivity {


    RecyclerView mainRecyclerView;

    // items here will be changed to a data structure such as ArrayList
    String[] items, descriptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize string arrays from strings.xml
        items = getResources().getStringArray(R.array.items);
        descriptions = getResources().getStringArray(R.array.descriptions);

        // find recycler view from activity_main.xml
        mainRecyclerView = findViewById(R.id.mainRecyclerView);

        // recycler view needs to set adapter to display the list to the screen
        MainRecyclerViewAdapter mainAdapter = new MainRecyclerViewAdapter(this, items, descriptions);
        mainRecyclerView.setAdapter(mainAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    // TODO: create a logout method



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
 * */