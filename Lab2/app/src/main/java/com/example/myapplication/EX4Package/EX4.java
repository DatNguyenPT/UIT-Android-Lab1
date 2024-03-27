package com.example.myapplication.EX4Package;

import android.app.Activity;
import android.text.Layout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AlertDialog;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX4 {
    private List<staff> staffs = new ArrayList<>();


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
