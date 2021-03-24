package com.example.bucketes;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "users.db";
    public static final String USERS_TABLE = "users_table";
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
    }

    // this is called the first time a database is accessed
    // this creates a new database
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        String createStatementTable = "create table " + USERS_TABLE + " (" + COL_USERNAME + " text primary key, " + COL_PASSWORD + " text)";

        MyDB.execSQL(createStatementTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists users");
    }

    public boolean addUser(UserModel userModel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues(); // this works similar to a hashmap with keys and values
        cv.put(COL_USERNAME, userModel.getUsername());
        cv.put(COL_PASSWORD, userModel.getPassword());

        long insert = db.insert(USERS_TABLE, null, cv);


        // -1 = fails to insert
        if (insert == -1)
            return false;
        else
            return true;

    }

}