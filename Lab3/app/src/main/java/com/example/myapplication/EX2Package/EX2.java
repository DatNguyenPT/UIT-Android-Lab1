package com.example.myapplication.EX2Package;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.EX1Package.EX1;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class EX2 extends AppCompatActivity {
    private ArrayList<Contact> mContacts ;
    private DatabaseHandler db;

    private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex2);
        db = new DatabaseHandler(this);
        ListView contactView = (ListView)findViewById(R.id.contactLV);
        mContacts = new ArrayList<>();
        db.deleteAllUsers();
        createContactList();

        // Reading all contacts
        Log.e("Reading: ", "Reading all contacts..");
        mContacts = db.getAllContacts();
        for (Contact cn : mContacts) {
            String log = "Id: " + cn.getId() + " ,Name: " + cn.getName() + ",Phone: " + cn.getPhoneNumber();
            // Writing Contacts to log
            Log.e("Name: ", log);
        }
        adapter = new ContactAdapter(this, mContacts);
        showData();

        contactView.setOnItemLongClickListener((parent, view, position, id) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(EX2.this);
            builder.setTitle("Delete Contact");
            builder.setMessage("Are you sure you want to delete this contact?");
            builder.setPositiveButton("Yes", (dialog, which) -> {
                Contact contact = mContacts.get(position);
                db.deleteContact(contact);
                mContacts.remove(position);
                adapter.notifyDataSetChanged();
            });
            builder.setNegativeButton("No", null);
            builder.show();
            return true;
        });

    }

    private void createContactList() {
        db.addContact(new Contact("Ravi", "9100000000"));
        db.addContact(new Contact("Srinivas", "9199999999"));
        db.addContact(new Contact("Tommy", "9522222222"));
        db.addContact(new Contact("Karthik", "9533333333"));
    }

    private void showData() {
        ListView lvUser = findViewById(R.id.contactLV);
        lvUser.setAdapter(adapter);
    }
}
