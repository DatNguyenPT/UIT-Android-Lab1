package com.example.myapplication.EX4Package;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class employeeAdapter extends ArrayAdapter<staff> {
    private Activity context;
    public employeeAdapter(Activity context, int layoutID, List<staff>
            objects) {
        super(context, layoutID, objects);
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView =
                    LayoutInflater.from(context).inflate(R.layout.item_employee, null,
                            false);
        }
// Get item
        staff employee = getItem(position);
// Get view
        TextView tvFullName = (TextView)
                convertView.findViewById(R.id.item_employee_tv_fullname);
        TextView tvPosition = (TextView)
                convertView.findViewById(R.id.item_employee_tv_position);
        ImageView ivManager = (ImageView)
                convertView.findViewById(R.id.item_employee_iv_manager);
        LinearLayout llParent = (LinearLayout)
                convertView.findViewById(R.id.ll_parent);
// Set fullname
        if (employee.getName()!=null) {
            tvFullName.setText(employee.getName());
        }
        else tvFullName.setText("");
// If this is a manager -> show icon manager. Otherwise, show Staff in tvPosition

        if (employee.isManager())
        {
            ivManager.setVisibility(View.VISIBLE);
            tvPosition.setVisibility(View.GONE);
        }
        else
        {
            ivManager.setVisibility(View.GONE);
            tvPosition.setVisibility(View.VISIBLE);
            tvPosition.setText("Staff");
        }
// Show different color backgrounds for 2 continuous employees
        if (position%2==0)
        {
            llParent.setBackgroundResource(R.color.white);
        }
        else
        {
            llParent.setBackgroundResource(R.color.light_blue);
        }
        return convertView;
    }
}
