package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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


    private Button cancel, save;
    private EditText title, story;
    private TextView  WarnTitle, WarnStory;
    //DBHelper DB;

    public static Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item);

        radioGroup = findViewById(R.id.radioGroup);
        title = findViewById(R.id.ItemTitle);
        story = findViewById(R.id.itemStory);
        cancel = findViewById(R.id.button1);
        save = findViewById(R.id.button2);
        WarnTitle = findViewById(R.id.textWarnNameS2);
        WarnStory = findViewById(R.id.textWarnNameS3);
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
                //Item item;

                // attempt to instantiate item
                try {
                    item = new Item(title.getText().toString(), story.getText().toString());
                } catch (Exception e) {
                    item = new Item("error", "error");
                    Toast.makeText(DetailActivity.this, "Error adding title", Toast.LENGTH_SHORT).show();
                }

                 boolean success = dbHelper.addItem(item);

                if (success && !title.getText().toString().equals("") && !story.getText().toString().equals("")) {
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // No title added
                    if (title.getText().toString().equals("")) {
                        WarnTitle.setVisibility(View.VISIBLE);
                        WarnTitle.setText("Required");
                    }
                    // No story entered
                    if (story.getText().toString().equals("")) {
                        WarnStory.setVisibility(View.VISIBLE);
                        WarnStory.setText("Required");
                    }

                    // Hide warning if fixed
                    if (!title.getText().toString().equals("")) {
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
