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
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class gridViewAdapter extends ArrayAdapter<food> {
    private Activity context;
    List<food> foodList = new ArrayList<>();
    public gridViewAdapter(Activity context, int layoutID, List<food> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null, false);
        }

        food f = getItem(position);
        TextView description = convertView.findViewById(R.id.description);
        ImageView foodImg = convertView.findViewById(R.id.img);

        if (f != null) {
            description.setText(f.getName());
            Resources res = context.getResources();
            int resId = f.getImage();
            Drawable drawable = res.getDrawable(resId);
            foodImg.setImageDrawable(drawable);
        }
        return convertView;
    }
}
