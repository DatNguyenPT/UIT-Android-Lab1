package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date


class EX3 : AppCompatActivity() {
    private var btnQuickJob: Button? = null
    private var btnSlowJob: Button? = null
    private var tvStatus: TextView? = null
    private var slowTask: SlowTask? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)
        findViewByIds()
        // Init slowtask
        slowTask = tvStatus?.let { SlowTask(this@EX3, it) }
        // Handle onClickListenner
        btnQuickJob!!.setOnClickListener {
            val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            tvStatus!!.text = sdf.format(Date())
        }
        btnSlowJob!!.setOnClickListener { slowTask?.execute() }
    }

    private fun findViewByIds() {
        btnQuickJob = findViewById<View>(R.id.btn_quick_job) as Button
        btnSlowJob = findViewById<View>(R.id.btn_slow_job) as Button
        tvStatus = findViewById<View>(R.id.tv_status) as TextView
    }
}

