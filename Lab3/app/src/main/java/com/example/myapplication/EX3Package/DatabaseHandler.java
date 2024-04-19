package com.example.myapplication.EX3Package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "qlsv.db";

    // Contacts table name
    private static final String DATABASE_TABLE = "StudentInfo";
    // Contacts Table Columns names
    private static final String STUDENT_ID = "MSSV";
    private static final String STUDENT_NAME = "HO TEN";
    private static final String STUDENT_CLASS = "LOP";
    SQLiteDatabase sqLiteDatabase;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Already Initialized
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Already Initialized
    }

    // Adding new student
    public void addStudent(Student student) {
        ContentValues initialValue = new ContentValues();
        initialValue.put(STUDENT_NAME, student.getName());
        initialValue.put(STUDENT_CLASS, student.getClassID());
        initialValue.put(STUDENT_ID, student.getId());
        sqLiteDatabase.insert(DATABASE_TABLE, null, initialValue);
    }

    // Updating student
    public void updateStudent(Student student) {
        String id = student.getId();
        String name = student.getName();
        String classId = student.getClassID();
        String query = "UPDATE " + DATABASE_TABLE + "\n"
                + "SET " + STUDENT_NAME + " = '" + name + "', "
                + STUDENT_CLASS + " = '" + classId + "' "
                + "WHERE " + STUDENT_ID + " = " + id;
        sqLiteDatabase.execSQL(query);
    }

    // Deleting single contact
    public boolean deleteStudent(Student student) {
        String id = student.getId();
        return sqLiteDatabase.delete(DATABASE_TABLE, STUDENT_ID + "=" + id,
                null) > 0;
    }

    public boolean isStudentExists(Student student) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + DATABASE_TABLE +
                " WHERE " + STUDENT_ID + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{student.getId()});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }
}
