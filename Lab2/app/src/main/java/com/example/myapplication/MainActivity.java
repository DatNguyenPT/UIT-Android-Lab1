package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.EX3Package.EX3;
import com.example.myapplication.EX4Package.EX4;
import com.example.myapplication.EX5Package.EX5;
import com.example.myapplication.EX6Package.ButtonAdapter;
import com.example.myapplication.EX6Package.EX6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Ex3
        setContentView(R.layout.ex3);

        ListView listView = findViewById(R.id.employeeList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.employeeNameInput);
        EditText idInput = findViewById(R.id.employeeIDInput);
        EX3 ex3 = new EX3();
        ex3.execute(listView, addButton, nameInput, idInput, this);

        // Ex4
        setContentView(R.layout.ex4);
        ListView listView = findViewById(R.id.staffList);
        Button addButton = findViewById(R.id.addEmployeeButton);
        EditText nameInput = findViewById(R.id.ex4EmployeeNameInput);
        EditText idInput = findViewById(R.id.ex4EmployeeIDInput);
        EX4 ex4 = new EX4();
        ex4.execute(listView, addButton, nameInput, idInput, this);

        // Ex5
        setContentView(R.layout.ex5);
        GridView gridView = findViewById(R.id.foodList);
        Spinner spinner = findViewById(R.id.spinner_thumbnail);
        Button addButton = findViewById(R.id.addFoodButton);
        EditText nameInput = findViewById(R.id.foodNameInput);
        EX5 ex5 = new EX5();
        ex5.execute(gridView, spinner, addButton, nameInput, this);
    }*/
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.main_layout);

      // Create a list to hold button objects
      ArrayList<Button> buttons = new ArrayList<>();
      int maxButtons = 6;
      for (int i = 1; i <= maxButtons; i++) {
          Button newButton = new Button(this);
          newButton.setText("Button " + i);
          buttons.add(newButton);
      }
      ButtonAdapter buttonAdapter = new ButtonAdapter(this, R.layout.menurecylerrow, buttons);
      GridView gridView = findViewById(R.id.exList);
      gridView.setAdapter(buttonAdapter);

  }
}
