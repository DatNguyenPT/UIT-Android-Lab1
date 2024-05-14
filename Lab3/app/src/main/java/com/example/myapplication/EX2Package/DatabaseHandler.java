package com.example.myapplication.EX2Package;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String DATABASE_TABLE = "contacts";
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";
    SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase(); // Initialize sqLiteDatabase
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                DATABASE_TABLE + "("
                + KEY_ID + " INTEGER PRIMARY KEY, "
                + KEY_NAME + " TEXT, "
                + KEY_PH_NO + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int
            newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // Create tables again
        onCreate(db);
    }
    // Adding new contact
    public void addContact(Contact contact) {
        ContentValues initialValue = new ContentValues();
        initialValue.put(KEY_NAME, contact.getName());
        initialValue.put(KEY_PH_NO, contact.getPhoneNumber());
        sqLiteDatabase.insert(DATABASE_TABLE, null, initialValue);
    }
    // Getting single contact
    @SuppressLint("Range")
    public Contact getContact(int id) {
        Contact foundContact = new Contact();
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_ID + " = " + id;
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while(cursor.moveToNext()){
            foundContact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            foundContact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
        }
        if (!foundContact.getName().equals("") && !foundContact.getPhoneNumber().equals("")){
            foundContact.setId(id);
        }
        return foundContact;
    }
    // Getting All Contacts
    @SuppressLint("Range")
    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact>contactList = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO}, null, null, null, null, null);
        while(cursor.moveToNext()){
            Contact foundContact = new Contact();
            foundContact.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            foundContact.setPhoneNumber(cursor.getString(cursor.getColumnIndex(KEY_PH_NO)));
            foundContact.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
            contactList.add(foundContact);
        }
        return contactList;
    }
    // Updating single contact
    public void updateContact(Contact contact) {
        int id = contact.getId();
        String name = contact.getName();
        String phoneNumber = contact.getPhoneNumber();
        String query = "UPDATE " + DATABASE_TABLE + "\n"
                + "SET " + KEY_NAME + " = '" + name + "', "
                + KEY_PH_NO + " = '" + phoneNumber + "' "
                + "WHERE " + KEY_ID + " = " + id;
        sqLiteDatabase.execSQL(query);
    }
    // Deleting single contact
    public boolean deleteContact(Contact contact) {
        int id = contact.getId();
        return sqLiteDatabase.delete(DATABASE_TABLE, KEY_ID + "=" + id,
                null) > 0;
    }
    public boolean deleteAllUsers() {
        boolean result = sqLiteDatabase.delete(DATABASE_TABLE, null, null) > 0;
        if(result) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }
        return result;
    }

}
