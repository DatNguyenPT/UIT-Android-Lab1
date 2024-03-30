package com.example.myapplication.EX5Package;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX5 {
    private List<food> foodList = new ArrayList<>();
    private List<Thumbnail>thumbnailList = new ArrayList<>();

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
                TextView des = activity.findViewById(R.id.description);
                ImageView star = activity.findViewById(R.id.promotionStarCheck);
                if(star != null){
                    star.setImageResource(R.drawable.star);
                }
                if (!name.isEmpty()) {
                    food newFood = new food();
                    newFood.setName(name);
                    if (checkBox.isChecked()) {
                        star.setVisibility(View.VISIBLE);
                        newFood.setPromotion(true);
                    } else {
                        star.setVisibility(View.INVISIBLE);
                        newFood.setPromotion(false);
                    }
                    des.setText(name);
                    newFood.setImage(R.drawable.star);
                    foodList.add(newFood);
                    Spinner spinner = activity.findViewById(R.id.spinner_thumbnail);
                    spinnerAdapter sAdapter = (spinnerAdapter) spinner.getAdapter();
                    if (sAdapter != null) {
                        sAdapter.notifyDataSetChanged();
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
                        gAdapter.notifyDataSetChanged();
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


    private boolean check(food newFood, List<food> foodList) {
        for (food s : foodList) {
            if (s.getName().equals(newFood.getName()))
                return true;
        }
        return false;
    }
}
