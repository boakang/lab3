package com.example.lab3;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    List<Student> students;
    DatabaseHelper db;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
        db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, int position) {
        Student s = students.get(position);
        holder.txtInfo.setText(s.getName() + " - " + s.getAge());

        holder.itemView.setOnClickListener(v -> showEditDialog(s, position));
        holder.itemView.setOnLongClickListener(v -> {
            db.deleteStudent(s.getId());
            students.remove(position);
            notifyItemRemoved(position);
            return true;
        });
    }

    private void showEditDialog(Student student, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_student, null);
        EditText edtName = view.findViewById(R.id.edt_update_name);
        EditText edtAge = view.findViewById(R.id.edt_update_age);

        edtName.setText(student.getName());
        edtAge.setText(String.valueOf(student.getAge()));

        new AlertDialog.Builder(context)
                .setTitle("Cập nhật sinh viên")
                .setView(view)
                .setPositiveButton("Lưu", (dialog, which) -> {
                    student.setName(edtName.getText().toString());
                    student.setAge(Integer.parseInt(edtAge.getText().toString()));
                    db.updateStudent(student);
                    notifyItemChanged(position);
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtInfo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtInfo = itemView.findViewById(R.id.txt_info);
        }
    }
}
