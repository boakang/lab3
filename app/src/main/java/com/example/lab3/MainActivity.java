package com.example.lab3;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DatabaseHandler db;
    private ListView listView;
    private ArrayAdapter<Contact> adapter;
    private List<Contact> contactList;
    private EditText etName, etPhone;
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHandler(this);
        listView = findViewById(R.id.list_view);
        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);
        btnAdd = findViewById(R.id.btn_add);

        loadData();

        btnAdd.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            if (!name.isEmpty() && !phone.isEmpty()) {
                db.addContact(new Contact(name, phone));
                etName.setText("");
                etPhone.setText("");
                loadData();
            }
        });

        // Click để sửa
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Contact contact = contactList.get(position);
            showUpdateDialog(contact);
        });

        // Long click để xóa
        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            Contact contact = contactList.get(position);
            db.deleteContact(contact);
            loadData();
            return true;
        });
    }

    private void showUpdateDialog(Contact contact) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cập nhật Contact");

        View view = getLayoutInflater().inflate(R.layout.dialog_update, null);
        EditText editName = view.findViewById(R.id.edit_name);
        EditText editPhone = view.findViewById(R.id.edit_phone);

        editName.setText(contact.getName());
        editPhone.setText(contact.getPhoneNumber());

        builder.setView(view);
        builder.setPositiveButton("Cập nhật", (dialog, which) -> {
            contact.setName(editName.getText().toString().trim());
            contact.setPhoneNumber(editPhone.getText().toString().trim());
            db.updateContact(contact);
            loadData();
        });

        builder.setNegativeButton("Hủy", null);
        builder.show();
    }

    private void loadData() {
        contactList = db.getAllContacts();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contactList);
        listView.setAdapter(adapter);
    }
}
