package com.example.myapplication.EX3Package;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;

import java.util.ArrayList;

public class EX3 extends AppCompatActivity {
    private ArrayList<Student> studentList;
    private RecyclerView recylerStudents;
    private studentAdapter studentAdapter;
    private DatabaseHandler databaseHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3);
        recylerStudents = findViewById(R.id.recylerView);
        studentList = new ArrayList<>();
        studentAdapter = new studentAdapter(this,studentList);
        recylerStudents.setAdapter(studentAdapter);
        recylerStudents.setLayoutManager(new LinearLayoutManager(this));
        Button insertButton = findViewById(R.id.insert);
        Button updateButton = findViewById(R.id.update);
        Button deleteButton = findViewById(R.id.delete);
        Button queryButton = findViewById(R.id.query);
        EditText name = findViewById(R.id.NameInput);
        EditText id = findViewById(R.id.MSSVInput);
        EditText classID = findViewById(R.id.ClassInput);
        addStudent(insertButton, id, classID, name, this);
        deleteStudent(deleteButton, id, classID, name, this);
        updateStudent(updateButton, id, classID, name, this);
    }

    public void addStudent(Button insertButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity){
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();
                String classID = classInput.getText().toString().trim();
                if (!name.isEmpty() && !id.isEmpty()) {
                    Student newStudent = new Student();
                    newStudent.setName(name);
                    newStudent.setId(id);
                    newStudent.setClassID(classID);
                    if (check(newStudent, studentList)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Sinh viên này đã tồn tại")
                                .setTitle("Trùng sinh viên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        studentList.add(newStudent);
                        databaseHandler.addStudent(newStudent);
                        studentAdapter.notifyDataSetChanged(); // Notify adapter of data change
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setMessage("Tên hoặc ID chưa thỏa")
                            .setTitle("Tên hoặc ID sai")
                            .setPositiveButton("Nhập", null)
                            .show();
                }
            }
        });
    }

    public void deleteStudent(Button deleteButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();
                String classID = classInput.getText().toString().trim();
                if (!name.isEmpty() && !id.isEmpty()) {
                    Student target = new Student();
                    target.setName(name);
                    target.setId(id);
                    target.setClassID(classID);
                    if (check(target, studentList)) {
                        databaseHandler.deleteStudent(target);
                        new AlertDialog.Builder(activity)
                                .setMessage("Sinh viên này đã bị xóa")
                                .setTitle("Xóa sinh viên thành công")
                                .setPositiveButton("OK", null)
                                .show();
                        studentAdapter.notifyDataSetChanged();
                    } else {
                        new AlertDialog.Builder(activity)
                                .setMessage("Kiểm tra lại MSSV, TÊN và LỚP")
                                .setTitle("Sinh viên này không tồn tại")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setMessage("Tên hoặc ID chưa thỏa")
                            .setTitle("Tên hoặc ID sai")
                            .setPositiveButton("Nhập", null)
                            .show();
                }
            }
        });
    }

    public void updateStudent(Button updateButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity){
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();
                String classID = classInput.getText().toString().trim();
                if (!name.isEmpty() && !id.isEmpty()) {
                    Student target = new Student();
                    target.setName(name);
                    target.setId(id);
                    target.setClassID(classID);
                    if (check(target, studentList)) {
                        databaseHandler.updateStudent(target);
                        new AlertDialog.Builder(activity)
                                .setMessage("Cập nhật thông tin sinh viên thành công")
                                .setTitle("Cập nhật thông tin sinh viên")
                                .setPositiveButton("OK", null)
                                .show();
                        studentAdapter.notifyDataSetChanged();
                    } else {
                        new AlertDialog.Builder(activity)
                                .setMessage("Kiểm tra lại MSSV, TÊN và LỚP")
                                .setTitle("Sinh viên này không tồn tại")
                                .setPositiveButton("OK", null)
                                .show();
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setMessage("Tên hoặc ID chưa thỏa")
                            .setTitle("Tên hoặc ID sai")
                            .setPositiveButton("Nhập", null)
                            .show();
                }
            }
        });
    }
    private boolean check(Student newStudent, ArrayList<Student> studentList) {
        return databaseHandler.isStudentExists(newStudent);
    }
}
