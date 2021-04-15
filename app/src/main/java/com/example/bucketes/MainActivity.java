package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bucketes.models.ItemModel;
import com.example.bucketes.models.UserModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mainRecyclerView;

    private UserModel user = LoginActivity.user;
    private ArrayList<ItemModel> items;


    private EditText etUsername, etPassword;
    private Dialog addItemDialog, logoutConfirmDialog;
    private FloatingActionButton btnAddItem;
    private MenuItem btnLogout;
    private Button btnStay, btnYes; /* popup buttons */

    /**
     * Upon click logs out the user and return to login screen.
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.btnMenuLogout:
                Toast.makeText(MainActivity.this, "Logout", Toast.LENGTH_SHORT).show();
                // interesting about the popups
                // logoutConfirmDialog.setContentView(R.layout.add_item_popup);
                // logoutConfirmDialog.show();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initialize views by id */
        etUsername = findViewById(R.id.etLoginUsername);
        etPassword = findViewById(R.id.etLoginPassword);
        btnAddItem = findViewById(R.id.btnAddItem);

        addItemDialog = new Dialog(this);
        logoutConfirmDialog = new Dialog(this);
        btnStay = findViewById(R.id.btnConfirmPopupStay);
        btnYes = findViewById(R.id.btnConfirmPopupYes);


        items = new ArrayList<>();

        /* test dynamic items */
        ItemModel item = new ItemModel("test", "test", "test", "test", "test");
        ItemModel item1 = new ItemModel("test1", "test1", "test1", "test1", "test1");
        ItemModel item2 = new ItemModel("test2", "test2", "test2", "test2", "test2");

        items.add(item);
        items.add(item1);


        /* add item popup */
        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItemDialog.setContentView(R.layout.add_item_popup);
                addItemDialog.show();
            }
        });



        /* ===== UNDER EXPERIMENT ===== */
        /* logout popup */
        // btnStay.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         addItemDialog.setContentView(R.layout.add_item_popup);
        //         logoutConfirmDialog.hide();
        //     }
        // });


        // /* change to login activity */
        // btnYes.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //         startActivity(intent);
        //     }
        // });


        // find recycler view from activity_main.xml
        mainRecyclerView = findViewById(R.id.mainRecyclerView);

        // recycler view needs to set adapter to display the list to the screen
        MainRecyclerViewAdapter mainAdapter = new MainRecyclerViewAdapter(this, items);
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