package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class EX3 : AppCompatActivity() {
    private var backButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)

        backButton = findViewById(R.id.backButton) as Button
        backButton!!.setOnClickListener {
            this@EX3.finish()
        }
    }

    /*private fun finishWithTransition() {
        val options = ActivityOptionsCompat.makeCustomAnimation(
            this@EX3,
            R.anim.anim_slide_in_left,
            R.anim.anim_slide_out_right
        ).toBundle()
        finish()
        startActivity(Intent(this, MainActivity::class.java), options)
    }*/
}
