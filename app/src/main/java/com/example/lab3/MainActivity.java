package com.example.lab3;

import android.database.Cursor;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import com.example.lab3.DbAdapter;


public class MainActivity extends AppCompatActivity {
    private DbAdapter dbAdapter;
    private List<String> users;
    private ArrayAdapter<String> userAdapter;
    private ListView lvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvUser = findViewById(R.id.lv_user);
        dbAdapter = new DbAdapter(this);
        dbAdapter.open();
        dbAdapter.deleteAllUsers(); // Xóa dữ liệu cũ

        // Thêm dữ liệu mẫu
        for (int i = 0; i < 10; i++) {
            dbAdapter.createUser("Nguyễn Văn An " + i);
        }

        // Lấy dữ liệu từ database
        users = new ArrayList<>();
        Cursor cursor = dbAdapter.getAllUsers();
        int nameIndex = cursor.getColumnIndex(DbAdapter.KEY_NAME); // Lấy index an toàn

        while (cursor.moveToNext()) {
            if (nameIndex != -1) {
                users.add(cursor.getString(nameIndex));
            }
        }
        cursor.close(); // Đừng quên đóng cursor!

        // Hiển thị dữ liệu lên ListView
        userAdapter = new ArrayAdapter<>(this, R.layout.item_user, users);
        lvUser.setAdapter(userAdapter);
    }
}