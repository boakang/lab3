package com.example.lab3;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText edtMSSV, edtHoTen, edtLop;
    Button btnInsert, btnDelete, btnUpdate, btnQuery;
    RecyclerView recyclerView;
    StudentAdapter adapter;
    ArrayList<Student> studentList;
    DatabaseHelper dbHelper;
    int selectedID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ánh xạ view
        edtMSSV = findViewById(R.id.edtMSSV);
        edtHoTen = findViewById(R.id.edtHoTen);
        edtLop = findViewById(R.id.edtLop);
        btnInsert = findViewById(R.id.btnInsert);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnQuery = findViewById(R.id.btnQuery);
        recyclerView = findViewById(R.id.recyclerView);

        dbHelper = new DatabaseHelper(this);
        studentList = new ArrayList<>();
        adapter = new StudentAdapter(studentList, student -> {
            selectedID = student.getId();
            edtMSSV.setText(String.valueOf(student.getId()));
            edtHoTen.setText(student.getName());
            edtLop.setText(student.getLop());
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        btnInsert.setOnClickListener(v -> {
            int id = Integer.parseInt(edtMSSV.getText().toString());
            String name = edtHoTen.getText().toString();
            String lop = edtLop.getText().toString();
            if (dbHelper.insertStudent(id, name, lop)) {
                Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                clearInput();
            } else {
                Toast.makeText(this, "ID đã tồn tại", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (dbHelper.deleteStudent(selectedID)) {
                Toast.makeText(this, "Đã xóa", Toast.LENGTH_SHORT).show();
                clearInput();
            } else {
                Toast.makeText(this, "Không tìm thấy sinh viên", Toast.LENGTH_SHORT).show();
            }
        });

        btnUpdate.setOnClickListener(v -> {
            String name = edtHoTen.getText().toString();
            String lop = edtLop.getText().toString();
            if (dbHelper.updateStudent(selectedID, name, lop)) {
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                clearInput();
            } else {
                Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        btnQuery.setOnClickListener(v -> loadData());
    }

    void loadData() {
        studentList.clear();
        studentList.addAll(dbHelper.getAllStudentObjects());
        adapter.notifyDataSetChanged();
    }

    void clearInput() {
        edtMSSV.setText("");
        edtHoTen.setText("");
        edtLop.setText("");
        selectedID = -1;
        loadData();
    }
}
