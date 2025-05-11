package com.example.lab3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "qlsv.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "students";

    private static final String COL_ID = "id";
    private static final String COL_NAME = "name";
    private static final String COL_CLASS = "lop";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Tạo bảng
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + "(" +
                COL_ID + " INTEGER PRIMARY KEY," +
                COL_NAME + " TEXT," +
                COL_CLASS + " TEXT)";
        db.execSQL(createTable);
    }

    // Nâng cấp bảng
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Thêm sinh viên
    public boolean insertStudent(int id, String name, String lop) {
        if (checkExist(id)) return false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_NAME, name);
        values.put(COL_CLASS, lop);
        db.insert(TABLE_NAME, null, values);
        db.close();
        return true;
    }

    // Lấy toàn bộ sinh viên
    public ArrayList<String> getAllStudents() {
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String lop = cursor.getString(2);
            list.add(id + " - " + name + " - " + lop);
        }
        cursor.close();
        return list;
    }

    // Cập nhật sinh viên
    public boolean updateStudent(int id, String name, String lop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NAME, name);
        values.put(COL_CLASS, lop);
        int result = db.update(TABLE_NAME, values, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Xóa sinh viên
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result > 0;
    }

    // Kiểm tra sinh viên đã tồn tại chưa
    private boolean checkExist(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_ID + " = ?", new String[]{String.valueOf(id)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Lấy danh sách sinh viên dạng đối tượng
    public ArrayList<Student> getAllStudentObjects() {
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM students", null);
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String lop = cursor.getString(2);
            list.add(new Student(id, name, lop));
        }
        cursor.close();
        return list;
    }

}
