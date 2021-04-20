package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.bucketes.dialogs.AddItemDialog;
import com.example.bucketes.dialogs.LogoutDialog;
import com.example.bucketes.models.Item;
import com.example.bucketes.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LogoutDialog.CustomDialogListener, AddItemDialog.CustomDialogListener {

    RecyclerView mainRecyclerView;

    private User user;
    private List<Item> items = new ArrayList<>();
    private DBHelper dbHelper = new DBHelper(MainActivity.this);   // create reference to db

    private FloatingActionButton btnAddItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = LoginActivity.user;
        items = dbHelper.getItems(user);

        /* initialize views by id */
        btnAddItem = findViewById(R.id.btnAddItem);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleBtnAddItem();
            }
        });


        // find recycler view from activity_main.xml
        mainRecyclerView = findViewById(R.id.mainRecyclerView);

        // recycler view needs to set adapter to display the list to the screen
        MainRecyclerViewAdapter mainAdapter = new MainRecyclerViewAdapter(this, items);
        mainRecyclerView.setAdapter(mainAdapter);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    /** This method displays the menu to the main_activity. */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    /** This function handles menu item selection. */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnMenuLogout:
                Toast.makeText(MainActivity.this, "Logout clicked", Toast.LENGTH_SHORT).show();
                openLogoutDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** This function opens up the menu logout dialog. */
    public void openLogoutDialog() {
        LogoutDialog logout = new LogoutDialog();
        logout.show(getSupportFragmentManager(), "Logout Dialog");
    }

    /** This function switches to login activity. */
    @Override
    public void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /** This function opens up the add item dialog. */
    public void openAddItemDialog() {
        AddItemDialog dialog = new AddItemDialog();
        System.out.println("testing: " + user.getUsername());
        dialog.show(getSupportFragmentManager(), "Add Item Dialog");
    }

    @Override
    public void addItem(String title) {
        /* instantiate new item */
        Item item = new Item(user.getUsername(), title);

        /* add item to db */
        dbHelper.addItem(item);

        /* display items to the screen */
        items.add(item);
    }

    public void handleBtnAddItem() {
        openAddItemDialog();
    }

}


/** Notes:
 *  1. MainRecyclerViewAdapter is necessary to display the list to the main menu.
 * */