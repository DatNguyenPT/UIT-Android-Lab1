package com.example.myapplication.EX2Package;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.example.myapplication.R;
import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {

    private Context mContext;
    private ArrayList<Contact> mContacts;

    public ContactAdapter(Context context, ArrayList<Contact> contacts) {
        super(context, 0, contacts);
        mContext = context;
        mContacts = contacts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Contact contact = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.userrow, parent, false);
        }

        // Lookup view for data population
        TextView nameTextView = convertView.findViewById(R.id.username);
        TextView phoneTextView = convertView.findViewById(R.id.userPhone);

        // Populate the data into the template view using the data object
        nameTextView.setText(contact.getName());
        phoneTextView.setText(contact.getPhoneNumber());

        // Return the completed view to render on screen
        return convertView;
    }
}
