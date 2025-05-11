package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    StudentAdapter adapter;
    List<Student> studentList;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        db = new DatabaseHelper(this);

        findViewById(R.id.btn_add).setOnClickListener(v -> showAddDialog());

        studentList = db.getAllStudents();
        adapter = new StudentAdapter(this, studentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void showAddDialog() {
        View view = getLayoutInflater().inflate(R.layout.dialog_add_student, null);
        EditText edtName = view.findViewById(R.id.edt_add_name);
        EditText edtAge = view.findViewById(R.id.edt_add_age);

        new AlertDialog.Builder(this)
                .setTitle("Thêm sinh viên")
                .setView(view)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = edtName.getText().toString();
                    int age = Integer.parseInt(edtAge.getText().toString());
                    Student s = new Student(name, age);
                    db.addStudent(s);
                    studentList.clear();
                    studentList.addAll(db.getAllStudents());
                    adapter.notifyDataSetChanged();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}
