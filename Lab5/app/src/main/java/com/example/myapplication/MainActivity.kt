package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        val buttons = ArrayList<Button>()
        val maxButtons = 5
        for (i in 1..maxButtons) {
            val newButton = Button(this)
            buttons.add(newButton)
        }
        val buttonAdapter = ButtonAdapter(this, R.layout.menurecylerrow, buttons)
        val gridView = findViewById<GridView>(R.id.exList)
        gridView.adapter = buttonAdapter
    }
}