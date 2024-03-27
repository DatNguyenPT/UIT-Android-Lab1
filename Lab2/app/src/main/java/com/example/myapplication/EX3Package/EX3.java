package com.example.myapplication.EX3Package;

import android.app.Activity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX3 {
    private List<employee> employees = new ArrayList<>();

    public void execute(ListView listView, Button addButton, EditText nameInput, EditText idInput, Activity activity) {
        setupListView(listView, activity);
        addNewEmployee(addButton, nameInput, idInput, activity);
    }

    private void setupListView(ListView listView, Activity activity) {
        ArrayAdapter<employee> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(arrayAdapter);
    }

    public void addNewEmployee(Button addButton, EditText nameInput, EditText idInput, Activity activity) {
        RadioGroup radioGroup = activity.findViewById(R.id.radioGroup);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = idInput.getText().toString().trim();
                String name = nameInput.getText().toString().trim();

                if (!name.isEmpty() && !id.isEmpty()) {
                    employee newEmployee;
                    if (radioGroup.getCheckedRadioButtonId() == R.id.partTimeButton) {
                        newEmployee = new employeePartTime();
                    } else {
                        newEmployee = new employeeFullTime();
                    }
                    newEmployee.setId(id);
                    newEmployee.setName(name);

                    ListView listView = activity.findViewById(R.id.employeeList);
                    ArrayAdapter<employee> arrayAdapter = (ArrayAdapter<employee>) listView.getAdapter();
                    if (check(newEmployee, employees)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Tên này đã tồn tại")
                                .setTitle("Trùng tên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        arrayAdapter.add(newEmployee);
                        arrayAdapter.notifyDataSetChanged();
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


    private boolean check(employee newEmployee, List<employee> employees) {
        for (employee employee : employees) {
            if (employee.getName().equals(newEmployee.getName()) && employee.getId().equals(newEmployee.getId()))
                return true;
        }
        return false;
    }
}
