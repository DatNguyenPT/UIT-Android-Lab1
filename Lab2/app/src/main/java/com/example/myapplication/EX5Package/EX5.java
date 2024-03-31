package com.example.myapplication.EX5Package;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.EX6Package.EX6;
import com.example.myapplication.EX6Package.HeroAdapter;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX5 extends AppCompatActivity {
    private List<food> foodList = new ArrayList<>();
    private List<Thumbnail>thumbnailList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex5);
        GridView gridView = findViewById(R.id.foodList);
        Spinner spinner = findViewById(R.id.spinner_thumbnail);
        Button addButton = findViewById(R.id.addFoodButton);
        EditText nameInput = findViewById(R.id.foodNameInput);
        EX5 ex5 = new EX5();
        ex5.execute(gridView, spinner, addButton, nameInput, this);
    }

    public void execute(GridView gridView, Spinner spinner, Button addButton, EditText nameInput, Activity activity) {
        setupAdapter(spinner, gridView, activity);
        addNewFood(addButton, nameInput, activity);
    }

    private void setupAdapter(Spinner spinner, GridView gridView, Activity activity) {
        thumbnailList.add(Thumbnail.THUMBNAIL_1);
        thumbnailList.add(Thumbnail.THUMBNAIL_2);
        thumbnailList.add(Thumbnail.THUMBNAIL_3);
        thumbnailList.add(Thumbnail.THUMBNAIL_4);
        spinnerAdapter sAdapter = new spinnerAdapter(activity, R.id.ll_spinnerParent, thumbnailList);
        spinner.setAdapter(sAdapter);
        gridViewAdapter gAdapter = new gridViewAdapter(activity, R.id.ll_gridViewParent, foodList);
        gridView.setAdapter(gAdapter);
    }
    public void addNewFood(Button addButton, EditText nameInput, Activity activity) {
        CheckBox checkBox = activity.findViewById(R.id.isPromotion);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameInput.getText().toString().trim();
                if (!name.isEmpty()) {
                    food newFood = new food();
                    Spinner spinner = activity.findViewById(R.id.spinner_thumbnail);
                    spinnerAdapter sAdapter = (spinnerAdapter) spinner.getAdapter();
                    if (sAdapter != null) {
                        sAdapter.notifyDataSetChanged();
                    }
                    newFood.setName(name);
                    Thumbnail selectedThumbnail = (Thumbnail) spinner.getSelectedItem();
                    int imageId = selectedThumbnail.getImg();
                    newFood.setImage(imageId);
                    if (checkBox.isChecked()) {
                        newFood.setPromotion(true);
                    } else {
                        newFood.setPromotion(false);
                    }

                    GridView gridView = activity.findViewById(R.id.foodList);
                    gridViewAdapter gAdapter = (gridViewAdapter) gridView.getAdapter();
                    if (gAdapter == null) {
                        gAdapter = new gridViewAdapter(activity, R.layout.item_gridview, foodList);
                        gridView.setAdapter(gAdapter);
                    }
                    if (check(newFood, foodList)) {
                        new AlertDialog.Builder(activity)
                                .setMessage("Món ăn này đã tồn tại")
                                .setTitle("Trùng món ăn")
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        gAdapter.add(newFood);
                        Toast.makeText(activity, "Thêm món mới thành công",
                                Toast.LENGTH_LONG).show();
                        gAdapter.notifyDataSetChanged();
                        nameInput.setText("");
                        CheckBox promote = activity.findViewById(R.id.isPromotion);
                        promote.setChecked(false);
                    }
                } else {
                    new AlertDialog.Builder(activity)
                            .setMessage("Chưa điền tên món ăn")
                            .setTitle("Tên món ăn chưa thỏa")
                            .setPositiveButton("Nhập", null)
                            .show();
                }
            }
        });
    }


    private boolean check(food newFood, List<food> allFoods) {
        for (food s : allFoods) {
            if (s.getName().equals(newFood.getName()))
                return true;
        }
        return false;
    }
}
