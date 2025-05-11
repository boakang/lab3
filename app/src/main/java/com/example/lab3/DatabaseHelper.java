package com.example.lab3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_TABLE = "users";
    private static final String DATABASE_NAME = "Database_Demo";
    private static final int DATABASE_VERSION = 1;

    // Câu lệnh SQL để tạo bảng users
    private static final String DATABASE_CREATE =
            "create table users (_id integer primary key autoincrement, "
                    + "name text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng khi database được tạo lần đầu tiên
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
}
