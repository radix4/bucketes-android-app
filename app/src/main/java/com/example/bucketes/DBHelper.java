package com.example.bucketes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;



import com.example.bucketes.models.Item;
import com.example.bucketes.models.User;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "users.db";
    public static final String USERS_TABLE = "users_table";
    public static final String ITEMS_TABLE = "items_table";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ITEM_ID = "item_id";
    public static final String COL_TITLE = "title";
    public static final String COL_STORY = "story";
    public static final String COL_COMPLETION_DATE = "completion_date";
    public static final String COL_STATUS = "status";
    RadioButton radioButton;

    /**
     * This function creates the database if the database has not been created.
     * */
    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    /**
     * This function creates database tables.
     * This function is called the first time a database is accessed.
     * */
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String createUsersTableStatement =
                "create table " + USERS_TABLE + " (" +
                        COL_USERNAME + " text primary key, " +
                        COL_PASSWORD + " text)";

        String createItemsTableStatement =
                "create table " + ITEMS_TABLE + " (" +
                        COL_ITEM_ID + " integer, " +
                        COL_USERNAME + " text, " +
                        COL_TITLE + " text, " +
                        COL_STORY + " text, " +
                        COL_COMPLETION_DATE + " text, " +
                        COL_STATUS + " text, " +
                        "primary key (" + COL_ITEM_ID + "), " +
                        "foreign key (" + COL_USERNAME + ") references " + USERS_TABLE + ")";


        /* create 2 tables into the db */
        MyDB.execSQL(createUsersTableStatement);
        MyDB.execSQL(createItemsTableStatement);
    }

    /**
     * On upgrade, drop older tables and create new tables.
     * */
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop table if exists " + USERS_TABLE);
        MyDB.execSQL("drop table if exists " + ITEMS_TABLE);

        onCreate(MyDB);
    }

    /**
     * This function validates if the UserModel entered by the user matches with the
     * data in the database.
     * */
    public boolean validateUser(User user) {
        SQLiteDatabase db = this.getReadableDatabase();

        // select * from users_table where username=""
        String queryString = "select * from " + USERS_TABLE + " where " + COL_USERNAME + "= ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{user.getUsername()});       // cursor is the result set from a SQL statement


        Log.d("COUNT", "validateUser: " + cursor.getCount());
        if (cursor.moveToFirst()) {       // found something
            String password = cursor.getString(1);
            Log.d("DBHelper", "cursor password: " + cursor.getString(1));

            if (user.getPassword().equals(password)) {     // check if password matches from db
                cursor.close();
                db.close();
                return true;
            } else {
                cursor.close();
                db.close();
                return false;
            }
        } else {
            Log.d("DBHelper", "Cursor fail!");
        }

        // very important to close the cursor and db when done
        cursor.close();
        db.close();
        return false;
    }

    /**
     * This function inserts the UserModel into the database.
     * */
    public boolean addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues(); // ContentValues works similar to a hashmap with keys and values
        cv.put(COL_USERNAME, user.getUsername());
        cv.put(COL_PASSWORD, user.getPassword());

        long insert = db.insert(USERS_TABLE, null, cv);

        db.close();

        return insert != -1;
    }

    /**
    * This function inserts the ItemModel into the database.
    * */
    public boolean addItem(Item item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        /* item's username and title must have */
        cv.put(COL_USERNAME, item.getUsername());
        cv.put(COL_TITLE, item.getTitle());
        cv.put(COL_STATUS, "planned");
        /* item's story, completion date, and status can be updated later */
        if (item.getStory() != null) cv.put(COL_STORY, item.getStory());
        if (item.getCompletionDate() != null) cv.put(COL_COMPLETION_DATE, item.getCompletionDate());
        // if (item.getStatus() != null) cv.put(COL_STATUS, item.getStatus());

        long insert = db.insert(ITEMS_TABLE, null, cv);

        db.close();

        return insert != -1;
    }

    /**
     * This function checks the database for the given user
     * and return the user's list of items.
     */
    public List<Item> getItems(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Item> items = new ArrayList<>();


        String query = "select * from users_table natural join items_table where  " + COL_USERNAME + "= ?";

        /* cursor is the result set from a SQL statement */
        Cursor cursor = db.rawQuery(query, new String[]{user.getUsername()});

        if (cursor.moveToFirst()) {
            do {
                /* caution: joined tables have different attributes, carefully check for each column  */
                int usernameIndex = cursor.getColumnIndex("username");
                int titleIndex = cursor.getColumnIndex("title");
                int storyIndex = cursor.getColumnIndex("story");
                int dateIndex = cursor.getColumnIndex("completion_date");
                int statusIndex = cursor.getColumnIndex("status");

                String username = cursor.getString(usernameIndex);
                String title = cursor.getString(titleIndex);
                String story = cursor.getString(storyIndex);
                String date = cursor.getString(dateIndex);
                String status = cursor.getString(statusIndex);

                Item item = new Item(username, title, story, date, status);
                items.add(item);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return items;
    }

    /**
     * This function deletes item with specified is from the database.
     * */
    public boolean deleteItem(String user, String title) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "DELETE FROM " + ITEMS_TABLE + " WHERE " + COL_USERNAME
                + " = '" + user + "' AND " + COL_TITLE + " = '" + title +"'";

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        else {
            cursor.close();
            db.close();
            return false;
        }
    }

    /**
     * This function updates item info in the db.
     * */
    public boolean updateItem(Item item, String newTitle, String newDate, String newStory, String newStatus) {
        if (newTitle.isEmpty()) return false;

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + ITEMS_TABLE + " SET " + COL_TITLE + " = '" + newTitle + "', " + COL_COMPLETION_DATE
                +  " = '" + newDate + "', " + COL_STORY + " = '" + newStory  + "', " + COL_STATUS + " ='" + newStatus + "' "
                + "WHERE " + COL_USERNAME + " = '" +  item.getUsername() + "' AND " + COL_TITLE + " = '" + item.getTitle() +"'";

            Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return true;
        }
        else {
            cursor.close();
            db.close();
            return false;
        }
    }
}