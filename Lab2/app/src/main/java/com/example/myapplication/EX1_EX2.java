package com.example.myapplication;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class EX1_EX2 {
    /*----------------------------------EX1 + EX2-------------------------------------------*/
    public void execute(ListView listView, Button addButton, EditText nameInput, TextView positionView, Activity activity){
        setupListView(listView, positionView, activity);
        setupAddButton(addButton, nameInput, activity);
    }

    private void setupListView(ListView listView, TextView positionView, Activity activity) {
        ArrayList<String> names = new ArrayList<>();
        names.add("Tý");
        names.add("Tèo");
        names.add("Bin");
        names.add("Bo");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(arrayAdapter);
        // View name + position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                positionView.setText("Position: " + position + " Value: " + names.get(position));
            }
        });
        // Delete name
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setMessage("Bạn có chắc sẽ xóa tên này ?");
                builder.setTitle("Xóa tên");
                builder.setCancelable(false);
                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        names.remove(position);
                        arrayAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    private void setupAddButton(Button addButton, EditText nameInput, Activity activity) {
        TextView positionView = activity.findViewById(R.id.positionView);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameInput.getText().toString().trim();
                if (!newName.isEmpty()) {
                    ListView listView = activity.findViewById(R.id.name_list);
                    ArrayAdapter<String> arrayAdapter = (ArrayAdapter<String>) listView.getAdapter();
                    if (arrayAdapter.getPosition(newName) != -1) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Tên này đã tồn tại")
                                .setTitle("Trùng tên")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        arrayAdapter.add(newName);
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setMessage("Bạn vẫn chưa nhập tên")
                            .setTitle("Tên không đúng")
                            .setPositiveButton("Nhập", null)
                            .show();
                }
            }
        });
    }
}
