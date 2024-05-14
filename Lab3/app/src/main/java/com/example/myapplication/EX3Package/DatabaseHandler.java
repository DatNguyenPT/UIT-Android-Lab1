package com.example.myapplication.EX3Package;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "qlsv";

    // Contacts table name
    static final String DATABASE_TABLE = "StudentInfo";
    // Contacts Table Columns names
    static final String STUDENT_ID = "MSSV";
    static final String STUDENT_NAME = "HOTEN";
    static final String STUDENT_CLASS = "LOP";
    SQLiteDatabase sqLiteDatabase;
    private ArrayList<Student> students = new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        sqLiteDatabase = this.getWritableDatabase(); // Initialize sqLiteDatabase
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " +
                DATABASE_TABLE + "("
                + STUDENT_ID + " INTEGER PRIMARY KEY, "
                + "\"" + STUDENT_NAME + "\"" + " TEXT, "
                + "\"" + STUDENT_CLASS + "\"" + " TEXT"
                + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        // Create tables again
        onCreate(db);
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
    public void updateStudent(String oldId, Student newStudent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STUDENT_NAME, newStudent.getName());
        values.put(STUDENT_CLASS, newStudent.getClassID());
        values.put(STUDENT_ID, newStudent.getId());

        // Update the record with the old ID
        db.update(DATABASE_TABLE, values, STUDENT_ID + " = ?", new String[]{oldId});
        db.close();
    }


    // Deleting single contact
    public boolean deleteStudent(Student student) {
        String id = student.getId();
        return sqLiteDatabase.delete(DATABASE_TABLE, STUDENT_ID + "=" + id,
                null) > 0;
    }

    public ArrayList<Student> getAllStudents() {
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE, new String[]{STUDENT_ID, "\"" + STUDENT_NAME + "\"", "\"" + STUDENT_CLASS + "\""}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Student foundStudent = new Student();
                int idColumnIndex = cursor.getColumnIndex(STUDENT_ID);
                int nameColumnIndex = cursor.getColumnIndex(STUDENT_NAME);
                int classColumnIndex = cursor.getColumnIndex(STUDENT_CLASS);
                if (idColumnIndex >= 0 && nameColumnIndex >= 0 && classColumnIndex >= 0) {
                    foundStudent.setId(cursor.getString(idColumnIndex));
                    foundStudent.setName(cursor.getString(nameColumnIndex));
                    foundStudent.setClassID(cursor.getString(classColumnIndex));
                    students.add(foundStudent);
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return students;
    }

    public SQLiteDatabase getDatabase() {
        return this.getWritableDatabase();
    }


}
