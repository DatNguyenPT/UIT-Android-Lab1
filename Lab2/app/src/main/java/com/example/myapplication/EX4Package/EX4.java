package com.example.myapplication.EX4Package;

import android.app.Activity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.EX5Package.EX5;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX4 extends AppCompatActivity {
    private List<staff> staffs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex4);
        ListView listView = findViewById(R.id.staffList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.ex4EmployeeNameInput);
        EditText idInput = findViewById(R.id.ex4EmployeeIDInput);
        EX4 ex4 = new EX4();
        ex4.execute(listView, addButton, nameInput, idInput, this);
    }


    public void execute(ListView listView, Button addButton, EditText nameInput, EditText idInput, Activity activity) {
        setupListView(listView, activity);
        addNewEmployee(addButton, nameInput, idInput, activity);
    }

    private void setupListView(ListView listView, Activity activity) {
        employeeAdapter edapter = new employeeAdapter(activity, 0, staffs);
        listView.setAdapter(edapter);
    }

    public void addNewEmployee(Button addButton, EditText nameInput, EditText idInput, Activity activity) {
        CheckBox checkBox = activity.findViewById(R.id.isManagerButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();

                if (!name.isEmpty() && !id.isEmpty()) {
                    staff newStaff = new staff();
                    newStaff.setName(name);
                    newStaff.setId(id);
                    if (checkBox.isChecked()) {
                        newStaff.setManager(true);
                        newStaff.setPosition("Manager");
                    } else {
                        newStaff.setManager(false);
                        newStaff.setPosition("Staff");
                    }
                    ListView listView = activity.findViewById(R.id.staffList);
                    employeeAdapter eAdapter = (employeeAdapter) listView.getAdapter();
                    if (eAdapter == null) {
                        eAdapter = new employeeAdapter(activity, R.layout.item_employee, staffs);
                        listView.setAdapter(eAdapter);
                    }
                    if (check(newStaff, staffs)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Nhân viên này đã tồn tại")
                                .setTitle("Trùng nhân viên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        eAdapter.add(newStaff);
                        eAdapter.notifyDataSetChanged();
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




    private boolean check(staff newStaff, List<staff> staffs) {
        for (staff s : staffs) {
            if (s.getId().equals(newStaff.getId()))
                return true;
        }
        return false;
    }
}
