package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketes.dialogs.AddItemDialog;
import com.example.bucketes.dialogs.LogoutDialog;
import com.example.bucketes.dialogs.SaveDialog;
import com.example.bucketes.models.Item;
import com.example.bucketes.models.User;

public class DetailActivity extends AppCompatActivity implements SaveDialog.CustomDialogListener {

    RadioGroup radioGroup;
    RadioButton radioButton;

    private EditText item;
    private Button save;
    private TextView  WarnTitle, WarnStory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item);

        radioGroup = findViewById(R.id.radioGroup);
        EditText title = findViewById(R.id.editTitle);
        EditText story = findViewById(R.id.editTitle);
        Button cancel = findViewById(R.id.button1);
        Button save = findViewById(R.id.button2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSaveDialog();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(DetailActivity.this);
                Item title;
                User user;

                // attempt to instantiate title
                try {
                    title = new Item(item.getText().toString(), story.getText().toString());
                } catch (Exception e) {
                    title = new Item("error", "error");
                    Toast.makeText(DetailActivity.this, "Error adding title", Toast.LENGTH_SHORT).show();
                }

                boolean success = dbHelper.addItem(title);

                if (success && !item.getText().toString().equals("") && !story.getText().toString().equals("")) {
                    Toast.makeText(DetailActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // No title added
                    if (item.getText().toString().equals("")) {
                        WarnTitle.setVisibility(View.VISIBLE);
                        WarnTitle.setText("Enter Title");
                    }
                    // No story entered
                    if (story.getText().toString().equals("")) {
                        WarnStory.setVisibility(View.VISIBLE);
                        WarnStory.setText("Enter Story");
                    }

                    // Hide warning if fixed
                    if (!item.getText().toString().equals("")) {
                        WarnTitle.setVisibility(View.INVISIBLE);
                    }
                    if (!story.getText().toString().equals("")) {
                        WarnStory.setVisibility(View.INVISIBLE);
                    }
                    Toast.makeText(DetailActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioId);

        Toast.makeText(this, "Selected: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    /** This function opens up the add item dialog. */
    public void openSaveDialog() {
        SaveDialog dialog = new SaveDialog();
        dialog.show(getSupportFragmentManager(), "Save Changes Dialog");
    }

    /** This function switches to Main activity. */
    public void goBack() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
