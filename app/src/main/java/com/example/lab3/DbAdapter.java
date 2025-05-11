package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.lab3.DatabaseHelper;

public class DbAdapter {
    public static final String KEY_ID = "_id";
    public static final String KEY_NAME = "name";

    private DatabaseHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    public DbAdapter(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public DbAdapter open() {
        sqLiteDatabase = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long createUser(String name) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_NAME, name);
        return sqLiteDatabase.insert(DatabaseHelper.DATABASE_TABLE, null, initialValues);
    }

    public boolean deleteUser(long rowId) {
        return sqLiteDatabase.delete(DatabaseHelper.DATABASE_TABLE, KEY_ID + "=" + rowId, null) > 0;
    }

    public boolean deleteAllUsers() {
        return sqLiteDatabase.delete(DatabaseHelper.DATABASE_TABLE, null, null) > 0;
    }


    public Cursor getAllUsers() {
        return sqLiteDatabase.query(DatabaseHelper.DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME}, null, null, null, null, null);
    }
}