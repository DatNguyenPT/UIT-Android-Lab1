package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class EX4 : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex4)
        viewPager = findViewById(R.id.viewPager)
        viewPager.adapter = Adapter()
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
    }
}
