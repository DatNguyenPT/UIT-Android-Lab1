package com.example.myapplication.EX5Package;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.myapplication.R;
import java.util.List;

public class gridViewAdapter extends ArrayAdapter<food> {
    private Activity context;

    public gridViewAdapter(Activity context, int layoutID, List<food> objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, parent, false);
        }

        food f = getItem(position);
        ImageView starCheck = convertView.findViewById(R.id.promotionStarCheck);
        TextView description = convertView.findViewById(R.id.description);
        ImageView foodImg = convertView.findViewById(R.id.img);

        if (f != null) {
            description.setText(f.getName());
            int imageResId = f.getImage();
            starCheck.setImageResource(R.drawable.star);
            starCheck.setBackground(null);
            description.setText(f.getName());
            if (f.isPromotion()){
                starCheck.setVisibility(View.VISIBLE);
            }else{
                starCheck.setVisibility(View.INVISIBLE);
            }
            foodImg.setImageResource(imageResId);
        }
        return convertView;
    }
}
