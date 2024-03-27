package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.EX3Package.EX3;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3);

        ListView listView = findViewById(R.id.employeeList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.employeeNameInput);
        EditText idInput = findViewById(R.id.employeeIDInput);
        EX3 ex3 = new EX3();
        ex3.execute(listView, addButton, nameInput, idInput, this);
    }


}
