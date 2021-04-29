package com.example.bucketes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bucketes.dialogs.SaveDialog;
import com.example.bucketes.models.Item;
import com.example.bucketes.models.User;

public class DetailActivity extends AppCompatActivity implements SaveDialog.CustomDialogListener {

    RadioGroup radioGroup;
    RadioButton radioButton;


    private User user;
    private Button cancel, save;
    private EditText title, story, date, status;
    private TextView  WarnTitle, WarnStory;
    private DBHelper dbHelper = new DBHelper(DetailActivity.this);   // create reference to db

    public static Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_item);

        radioGroup = findViewById(R.id.radioGroup);
        title = findViewById(R.id.itemTitle);
        story = findViewById(R.id.itemStory);
        date = findViewById(R.id.itemDate);
        cancel = findViewById(R.id.button1);
        save = findViewById(R.id.button2);
        WarnTitle = findViewById(R.id.textWarnNameS2);
        WarnStory = findViewById(R.id.textWarnNameS3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        user = LoginActivity.user;

        // Get item title from main activity
        Item passedItem = (Item) getIntent().getSerializableExtra("item");

        // Set item title
        title.setText(passedItem.getTitle(), TextView.BufferType.EDITABLE);

        // Get item story from db
        //String storyTxt = dbHelper.getStory(dbHelper.getCursor(user.getUsername(), itemTitle));
        story.setText(passedItem.getStory(), TextView.BufferType.EDITABLE);

        // Get item completion date from db
        // String dateTxt = dbHelper.getDate(dbHelper.getCursor(user.getUsername(), itemTitle));
        date.setText(passedItem.getCompletionDate(), TextView.BufferType.EDITABLE);

        // Get item status from db
        String statusTxt = passedItem.getStatus();
        switch (statusTxt) {
            case "planned":
                radioButton = findViewById(R.id.planned);
                break;
            case "in progress":
                radioButton = findViewById(R.id.in_progress);
                break;
            case "completed":
                radioButton = findViewById(R.id.completed);
                break;
        }
        radioButton.setChecked(true);

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
