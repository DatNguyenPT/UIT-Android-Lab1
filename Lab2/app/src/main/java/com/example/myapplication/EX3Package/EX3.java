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

    public void execute(ListView listView, Button addButton, EditText nameInput, EditText idInput, Activity activity){
        setupListView(listView, activity);
        addNewEmployee(addButton, nameInput,idInput, activity);
    }
    private void setupListView(ListView listView, Activity activity) {
        List<employee>employees = new ArrayList<>();
        ArrayAdapter<employee> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, employees);
        listView.setAdapter(arrayAdapter);
    }

    public void addNewEmployee(Button addButton, EditText nameInput, EditText idInput, Activity activity){
        List<employee>employees = new ArrayList<>();
        RadioGroup radioGroup = activity.findViewById(R.id.radioGroup);
        employee newEmployee;
        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId == R.id.partTimeButton){
            newEmployee = new employeePartTime();
        }else{
            newEmployee = new employeeFullTime();
        }
        String id = activity.findViewById(R.id.employeeIDInput).toString();
        String name = activity.findViewById(R.id.employeeNameInput).toString();
        newEmployee.setId(id);
        newEmployee.setName(name);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!newEmployee.getName().equals("") && !newEmployee.getId().equals("")) {
                    ListView listView = activity.findViewById(R.id.employeeList);
                    employees.add(newEmployee);
                    ArrayAdapter<employee> arrayAdapter = (ArrayAdapter<employee>) listView.getAdapter();
                    if (arrayAdapter.getPosition(newEmployee) != -1) {
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
}

