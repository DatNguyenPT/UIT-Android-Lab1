package com.example.myapplication.EX7Package;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.EX4Package.staff;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX7 extends AppCompatActivity {
    private ArrayList<staff>staffList;
    private RecyclerView staffRecyler;
    private employeeAdapterv2 eAdapterv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex7);
        Button addButton = findViewById(R.id.add);
        EditText nameInput = findViewById(R.id.ex7EmployeeNameInput);
        EditText idInput = findViewById(R.id.ex7EmployeeIDInput);
        staffRecyler = findViewById(R.id.ex7EmployeeList);
        staffList = new ArrayList<>();
        eAdapterv2 = new employeeAdapterv2(this,staffList);
        staffRecyler.setAdapter(eAdapterv2);
        staffRecyler.setLayoutManager(new LinearLayoutManager(this)); // Set linear type
        addNewEmployee(addButton, nameInput, idInput, this);
        eAdapterv2.notifyItemInserted(staffList.size() - 1);
    }

    public void addNewEmployee(Button addButton, EditText nameInput, EditText idInput, Activity activity) {
        CheckBox checkBox = activity.findViewById(R.id.ex7IsManagerButton);
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
                    /*RecyclerView recyclerView = activity.findViewById(R.id.ex7EmployeeList);
                    employeeAdapterv2 tempAdapter = (employeeAdapterv2) recyclerView.getAdapter();
                    if (tempAdapter == null) {
                        tempAdapter = new employeeAdapterv2(activity, staffList);
                        recyclerView.setAdapter(tempAdapter);
                    }*/
                    if (check(newStaff, staffList)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Nhân viên này đã tồn tại")
                                .setTitle("Trùng nhân viên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        staffList.add(newStaff);
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
