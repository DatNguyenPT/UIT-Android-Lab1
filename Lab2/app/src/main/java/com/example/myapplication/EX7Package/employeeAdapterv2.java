package com.example.myapplication.EX7Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.EX4Package.staff;
import com.example.myapplication.R;

import java.util.ArrayList;

public class employeeAdapterv2 extends RecyclerView.Adapter<employeeAdapterv2.ViewHolder> {
    private Context context;
    private ArrayList<staff>staffList;

    public employeeAdapterv2(Context context, ArrayList<staff> staffList) {
        this.context = context;
        this.staffList = staffList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View employeeView = inflater.inflate(R.layout.item_employee_ex7, parent,
                false);
        employeeAdapterv2.ViewHolder viewHolder = new employeeAdapterv2.ViewHolder(employeeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull employeeAdapterv2.ViewHolder holder, int position)
    {
        staff s = staffList.get(position);
        Glide.with(context)
                .load(R.drawable.ic_manager)
                .into(holder.staffImage);
        holder.fullName.setText(s.getName());
        holder.position.setText(s.getPosition());
        if (s.isManager()){
            holder.position.setVisibility(View.GONE);
            holder.staffImage.setVisibility(View.VISIBLE);
        }else{
            holder.staffImage.setVisibility(View.GONE);
            holder.position.setVisibility(View.VISIBLE);
        }
        if (position%2==0)
        {
            holder.ex7Parent.setBackgroundResource(R.color.white);
        }
        else
        {
            holder.ex7Parent.setBackgroundResource(R.color.light_blue);
        }

    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView staffImage;
        private TextView fullName;
        private TextView position;
        private LinearLayout ex7Parent;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            staffImage = itemView.findViewById(R.id.managerImage);
            fullName = itemView.findViewById(R.id.employeeFullName);
            position =  itemView.findViewById(R.id.employeePosition);
            ex7Parent = itemView.findViewById(R.id.ex7_parent);
        }
    }
}
