package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bucketes.dialogs.LogoutDialog;
import com.example.bucketes.models.Item;
import com.example.bucketes.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LogoutDialog.CustomDialogListener {

    RecyclerView mainRecyclerView;

    private User user = LoginActivity.user;
    private ArrayList<Item> items;

    private EditText etUsername, etPassword;
    private FloatingActionButton btnAddItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize views by id */
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnAddItem = findViewById(R.id.btnAddItem);

        items = new ArrayList<>();

        /* test dynamic items */
        Item item = new Item("test", "superlongsuperlongsuperlongsuperlongsuperlongsuperlongsuperlongsuperlong", "test", "test", "test");
        Item item1 = new Item("test1", "test1", "test1", "test1", "test1");
        Item item2 = new Item("test2", "test2", "test2", "test2", "test2");

        items.add(item);
        items.add(item1);


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
                openDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /** This function opens up the menu logout dialog. */
    public void openDialog() {
        LogoutDialog logout = new LogoutDialog();
        logout.show(getSupportFragmentManager(), "Logout Dialog");
    }

    /** This function switches to login activity. */
    @Override
    public void logout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}


/** Notes:
 *  1. MainRecyclerViewAdapter is necessary to display the list to the main menu.
 * */