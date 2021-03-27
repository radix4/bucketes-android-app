package com.example.bucketes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.bucketes.models.UserModel;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "users.db";
    public static final String USERS_TABLE = "users_table";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";
    public static final String COL_ITEM_ID = "item_id";
    public static final String COL_TITLE = "title";
    public static final String COL_STORY = "story";
    public static final String COL_COMPLETION_DATE = "completion_date";
    public static final String COL_STATUS = "status";
    public static final String ITEMS_TABLE = "items_table";

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
     * This function inserts the UserModel into the database.
     * */
    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues(); // ContentValues works similar to a hashmap with keys and values
        cv.put(COL_USERNAME, userModel.getUsername());
        cv.put(COL_PASSWORD, userModel.getPassword());

        long insert = db.insert(USERS_TABLE, null, cv);

        /* ==== test 2nd table ====
        * Status: success */
        ContentValues cv2 = new ContentValues();
        cv2.put(COL_USERNAME, "test");
        cv2.put(COL_TITLE, "test");
        cv2.put(COL_STORY, "test");
        cv2.put(COL_COMPLETION_DATE, "test");
        cv2.put(COL_STATUS, "test");
        long insert2 = db.insert(ITEMS_TABLE, null, cv2);
        /* ==== test 2nd table ==== */

        // -1 = fails to insert
        if (insert == -1)
            return false;
        else
            return true;
    }

    /**
     * This function validates if the UserModel entered by the user matches with the
     * data in the database.
     * */
    public boolean validateUser(UserModel userModel) {
        SQLiteDatabase db = this.getReadableDatabase();

        // select * from users_table where username=""
        String queryString = "select * from " + USERS_TABLE + " where " + COL_USERNAME + "= ?";
        Cursor cursor = db.rawQuery(queryString, new String[]{userModel.getUsername()});       // cursor is the result set from a SQL statement


        Log.d("COUNT", "validateUser: " + cursor.getCount());
        if (cursor.moveToFirst()) {       // found something
            String password = cursor.getString(1);
            Log.d("DBHelper", "cursor password: " + cursor.getString(1));

            if (userModel.getPassword().equals(password)) {     // check if password matches from db
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

}