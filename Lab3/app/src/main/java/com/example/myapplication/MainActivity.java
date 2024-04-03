package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        ArrayList<Button> buttons = new ArrayList<>();
        int maxButtons = 6;
        for (int i = 1; i <= maxButtons; i++) {
            Button newButton = new Button(this);
            buttons.add(newButton);
        }
        ButtonAdapter buttonAdapter = new ButtonAdapter(this, R.layout.menurecylerrow, buttons);
        GridView gridView = findViewById(R.id.exList);
        gridView.setAdapter(buttonAdapter);
    }
}