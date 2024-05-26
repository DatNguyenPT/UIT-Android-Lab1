package com.example.myapplication.EX3Package;

import static com.example.myapplication.EX3Package.DatabaseHandler.DATABASE_TABLE;
import static com.example.myapplication.EX3Package.DatabaseHandler.STUDENT_ID;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.util.ArrayList;


public class EX3 extends AppCompatActivity {
    private ArrayList<Student> studentList;
    private studentAdapter studentAdapter;
    private DatabaseHandler databaseHandler;
    private ListView studentListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3);
        databaseHandler = new DatabaseHandler(this);
        studentList = new ArrayList<>();
        studentListView = findViewById(R.id.studentLV);
        studentAdapter = new studentAdapter(this, studentList);
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
        query(queryButton, id, classID, name, this);
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
                    if (checkStudentExist(newStudent.getId())) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Sinh viên này đã tồn tại")
                                .setTitle("Trùng sinh viên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        studentList.add(newStudent);
                        databaseHandler.addStudent(newStudent);
                        Toast.makeText(EX3.this, "Đã thêm sinh viên vào db", Toast.LENGTH_SHORT).show();
                        //studentAdapter.notifyDataSetChanged(); // Notify adapter of data change
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

    public void deleteStudent(Button deleteButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity) {
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
                    if (checkStudentExist(id)) {
                        databaseHandler.deleteStudent(target);

                        // Remove the student from the list
                        for (Student student : studentList) {
                            if (student.getId().equals(id)) {
                                studentList.remove(student);
                                break;
                            }
                        }

                        new AlertDialog.Builder(activity)
                                .setMessage("Sinh viên này đã bị xóa")
                                .setTitle("Xóa sinh viên thành công")
                                .setPositiveButton("OK", null)
                                .show();
                        /*studentAdapter = new studentAdapter(EX3.this, studentList);
                        studentListView.setAdapter(studentAdapter);*/
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


    public void updateStudent(Button updateButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity) {
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldId = idInput.getText().toString().trim();
                String newName = nameInput.getText().toString().trim();
                String newClassID = classInput.getText().toString().trim();
                String newId = idInput.getText().toString().trim();  // Assuming idInput is the new ID input

                if (!newName.isEmpty() && !newId.isEmpty()) {
                    // Check if the new ID is different from the old ID and if it already exists
                    if (!oldId.equals(newId) && checkStudentExist(newId)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("MSSV mới đã tồn tại. Vui lòng chọn MSSV khác.")
                                .setTitle("Trùng MSSV")
                                .setPositiveButton("OK", null)
                                .show();
                        return;
                    }

                    Student target = new Student();
                    target.setName(newName);
                    target.setId(newId);
                    target.setClassID(newClassID);

                    if (checkStudentExist(oldId)) {
                        databaseHandler.updateStudent(oldId, target);

                        // Update the student list
                        for (Student student : studentList) {
                            if (student.getId().equals(oldId)) {
                                student.setName(newName);
                                student.setId(newId);
                                student.setClassID(newClassID);
                                break;
                            }
                        }

                        new AlertDialog.Builder(activity)
                                .setMessage("Cập nhật thông tin sinh viên thành công")
                                .setTitle("Cập nhật thông tin sinh viên")
                                .setPositiveButton("OK", null)
                                .show();
                        //studentAdapter.notifyDataSetChanged();
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

    public void query(Button querryButton, EditText idInput, EditText classInput, EditText nameInput, Activity activity){
        querryButton.setOnClickListener(new View.OnClickListener() {
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
                    String studentInfo = "Sinh viên: " + target.getName() + ", " + target.getClassID() + ", " + target.getId();
                    if (checkStudentExist(id)) {
                        ArrayList<Student>tempList = new ArrayList<>();
                        tempList.add(target);
                        studentAdapter = new studentAdapter(EX3.this, tempList);
                        studentListView.setAdapter(studentAdapter);
                        /*new AlertDialog.Builder(activity)
                                .setMessage(studentInfo)
                                .setTitle("Tìm kiếm sinh viên")
                                .setPositiveButton("OK", null)
                                .show();*/
                        studentAdapter.notifyDataSetChanged();
                    } else {
                        studentInfo += " không tồn tại trong danh sách";
                        new AlertDialog.Builder(activity)
                                .setMessage(studentInfo)
                                .setTitle("Tìm kiếm sinh viên")
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
    private boolean checkStudentExist(String id) {
        SQLiteDatabase db = databaseHandler.getWritableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + STUDENT_ID + " = ? COLLATE NOCASE";
        Cursor cursor = db.rawQuery(query, new String[]{id});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

}
