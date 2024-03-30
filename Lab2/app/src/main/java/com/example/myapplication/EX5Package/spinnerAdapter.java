package com.example.myapplication.EX5Package;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;

import java.util.List;

public class spinnerAdapter extends ArrayAdapter<Thumbnail> {
    private Activity context;
    private int id;

    public spinnerAdapter(Activity context, int layoutID, List<Thumbnail> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        }

        Thumbnail thumbnail = getItem(position);
        TextView thumbnailOrder = convertView.findViewById(R.id.thumbnailOrder);
        ImageView foodImage = convertView.findViewById(R.id.foodImage);

        if (thumbnail != null) {
            thumbnailOrder.setText(thumbnail.getName());
            Resources res = context.getResources();
            int resId = thumbnail.getImg();
            Drawable drawable = res.getDrawable(resId);
            foodImage.setImageDrawable(drawable);
        }

        return convertView;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_spinner, null, false);
        }

        Thumbnail thumbnail = getItem(position);
        TextView thumbnailOrder = convertView.findViewById(R.id.thumbnailOrder);
        ImageView foodImage = convertView.findViewById(R.id.foodImage);

        if (thumbnail != null) {
            thumbnailOrder.setText(thumbnail.getName());
            Resources res = context.getResources();
            int resId = thumbnail.getImg();
            Drawable drawable = res.getDrawable(resId);
            foodImage.setImageDrawable(drawable);
        }
        id = position;
        return convertView;
    }
    public int getId(){
        return this.id;
    }
}
