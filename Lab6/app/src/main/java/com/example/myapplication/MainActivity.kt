package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.GridView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttons = ArrayList<Button>()
        val maxButtons = 3
        for (i in 1..maxButtons) {
            val newButton = Button(this)
            buttons.add(newButton)
        }
        val buttonAdapter = ButtonAdapter(this, R.layout.menurecylerrow, buttons)
        val gridView = findViewById<GridView>(R.id.exList)
        gridView.adapter = buttonAdapter
    }
}