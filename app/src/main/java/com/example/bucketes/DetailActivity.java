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
    private Button cancel, save;
    private EditText title, story, date;
    private TextView  WarnTitle;
    String statusTxt, titleTxt, storyTxt, dateTxt, newStatusTxt, newTitleTxt, newStoryTxt, newDateTxt;
    public static Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Item passedItem = (Item) getIntent().getSerializableExtra("item"); // Get item title from main activity
        setContentView(R.layout.detailed_item);

        radioGroup = findViewById(R.id.radioGroup);
        title = findViewById(R.id.itemTitle);
        story = findViewById(R.id.itemStory);
        date = findViewById(R.id.itemDate);
        cancel = findViewById(R.id.button1);
        save = findViewById(R.id.button2);
        WarnTitle = findViewById(R.id.textWarnNameS2);

        statusTxt = passedItem.getStatus();
        titleTxt = passedItem.getTitle();
        storyTxt = passedItem.getStory();
        dateTxt = passedItem.getCompletionDate();

        // Set item title
        title.setText(passedItem.getTitle());

        // Get item story from db
        story.setText(passedItem.getStory());

        // Get item completion date from db
        date.setText(passedItem.getCompletionDate());

        // Get item status from db
        if (statusTxt != null) {
            switch (statusTxt) {
                case "planned":
                    radioButton = findViewById(R.id.planned);
                    radioButton.setChecked(true);
                    break;
                case "in progress":
                    radioButton = findViewById(R.id.in_progress);
                    radioButton.setChecked(true);
                    break;
                case "completed":
                    radioButton = findViewById(R.id.completed);
                    radioButton.setChecked(true);
                    break;
            }
        }

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(DetailActivity.this);

                newDateTxt = date.getText().toString();
                newTitleTxt = title.getText().toString();
                newStoryTxt = story.getText().toString();

                if (titleTxt == newTitleTxt && storyTxt == newStoryTxt && dateTxt == newDateTxt) {
                    goBack();
                } else {
                    openSaveDialog();
                    boolean success = dbHelper.updateItem(passedItem, newTitleTxt, newDateTxt, newStoryTxt, statusTxt);

                    if (success && !title.getText().toString().equals("")) {
                        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbHelper = new DBHelper(DetailActivity.this);

                if (title.getText().toString().equals("")) {
                    WarnTitle.setVisibility(View.VISIBLE);
                    WarnTitle.setText("Required");
                    return;
                }

                boolean success = dbHelper.updateItem(passedItem, title.getText().toString(), date.getText().toString(), story.getText().toString(), newStatusTxt);




                if (success && !title.getText().toString().equals("")){
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // No title added
                    if (title.getText().toString().equals("")) {
                        WarnTitle.setVisibility(View.VISIBLE);
                        WarnTitle.setText("Required");
                    }

                    // Hide warning if fixed
                    if (!title.getText().toString().equals("")) {
                        WarnTitle.setVisibility(View.INVISIBLE);
                    }
                    Intent intent = new Intent(DetailActivity.this, MainActivity.class);
                    startActivity(intent);

                }

               // }
               // Toast.makeText(DetailActivity.this, "Failed to save", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void checkButton(View v){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        newStatusTxt = radioButton.getText().toString();
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

    public void goSave() {
        save.callOnClick();
    }

}
