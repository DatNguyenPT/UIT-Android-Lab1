package com.example.myapplication.EX3Package;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class EX3Extra extends AppCompatActivity {
    private static final String DB_PATH_SUFFIX = "/databases/";
    private static final String DATABASE_NAME = "qlsv.db";
    private SQLiteDatabase database = null;

    // Declare ListView and other UI elements
    private ListView lv;
    private ArrayList<String> mylist;
    private ArrayAdapter<String> myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex1);

        // Copy database from assets if needed
        processCopy();

        // Open the database
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Initialize ListView
        lv = findViewById(R.id.lv_user);
        mylist = new ArrayList<>();
        myadapter = new ArrayAdapter<>(this, R.layout.item_user, mylist);
        lv.setAdapter(myadapter);

        // Query database and update ListView
        queryDatabase();
    }

    private void queryDatabase() {
        Cursor c = database.query("studentinfo", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                String data = c.getString(0) + "-" + c.getString(1) + "-" + c.getString(2);
                mylist.add(data);
            } while (c.moveToNext());
        }
        c.close();
        myadapter.notifyDataSetChanged();
    }

    private void processCopy() {
        // Check if the database already exists
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "Copying successful from Assets folder", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private String getDatabasePath() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = getDatabasePath();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);
            if (!f.exists()) f.mkdir();

            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }

            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
