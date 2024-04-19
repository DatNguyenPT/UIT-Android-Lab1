package com.example.myapplication.EX3Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class studentAdapter extends RecyclerView.Adapter<studentAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Student> studentList;

    public studentAdapter(Context mContext, ArrayList<Student> studentList){
        this.mContext = mContext;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.studentrow, parent,
                false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Student s = studentList.get(position);
        holder.info.setText(s.getName() + " - " + s.getId());
    }
    @Override
    public int getItemCount() {
        return studentList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            info = itemView.findViewById(R.id.studentInfo);
        }
    }
}
