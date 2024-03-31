package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import com.example.myapplication.EX3Package.EX3;
import com.example.myapplication.EX4Package.EX4;
import com.example.myapplication.EX5Package.EX5;
import com.example.myapplication.EX6Package.EX6;
import com.example.myapplication.EX7Package.EX7;

import java.util.ArrayList;
import java.util.List;

public class ButtonAdapter extends ArrayAdapter<Button> {
    private Context context;
    private ArrayList<Integer> id;

    public ButtonAdapter(Activity context, int layoutID, List<Button> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @SuppressLint("SuspiciousIndentation")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.menurecylerrow, parent, false);
        }

        Button button = (Button) convertView.findViewById(R.id.exOption);
        if(position == 0){
            button.setText("EX1_2");
        }else
        button.setText("EX " + (position + 2)); // Set button text based on position

        // Set click listener to start corresponding intent
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Determine which intent to start based on button position
                Intent intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, EX1_EX2.class);
                        break;
                    case 1:
                        intent = new Intent(context, EX3.class);
                        break;
                    case 2:
                        intent = new Intent(context, EX4.class);
                        break;
                    case 3:
                        intent = new Intent(context, EX5.class);
                        break;
                    case 4:
                        intent = new Intent(context, EX6.class);
                        break;
                    case 5:
                        intent = new Intent(context, EX7.class);
                        break;
                    default:
                        intent = new Intent(context, EX1_EX2.class);
                        break;
                }
                context.startActivity(intent); // Start the intent
            }
        });

        return convertView;
    }
}

