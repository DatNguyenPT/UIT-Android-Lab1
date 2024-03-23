package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex2);

        ListView listView = findViewById(R.id.name_list);
        Button addButton = findViewById(R.id.addButton);
        EditText nameInput = findViewById(R.id.nameInput);
        TextView positionView = findViewById(R.id.positionView);

        EX1_EX2 ex1_ex2 = new EX1_EX2();
        ex1_ex2.execute(listView, addButton, nameInput, positionView, this);
    }



}
