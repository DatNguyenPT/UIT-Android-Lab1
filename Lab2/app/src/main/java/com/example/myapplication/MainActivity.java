package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.EX4Package.EX4;
import com.example.myapplication.EX5Package.EX5;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ex3
        /*setContentView(R.layout.ex3);

        ListView listView = findViewById(R.id.employeeList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.employeeNameInput);
        EditText idInput = findViewById(R.id.employeeIDInput);
        EX3 ex3 = new EX3();
        ex3.execute(listView, addButton, nameInput, idInput, this);*/

        // Ex4
        /*setContentView(R.layout.ex4);
        ListView listView = findViewById(R.id.staffList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.ex4EmployeeNameInput);
        EditText idInput = findViewById(R.id.ex4EmployeeIDInput);
        EX4 ex4 = new EX4();
        ex4.execute(listView, addButton, nameInput, idInput, this);*/

        setContentView(R.layout.ex5);
        GridView gridView = findViewById(R.id.foodList);
        Spinner spinner = findViewById(R.id.spinner_thumbnail);
        Button addButton = findViewById(R.id.addFoodButton);
        EditText nameInput = findViewById(R.id.foodNameInput);
        EX5 ex5 = new EX5();
        ex5.execute(gridView, spinner, addButton, nameInput, this);
    }


}
