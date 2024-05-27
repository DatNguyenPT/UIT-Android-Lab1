package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EX2 : AppCompatActivity() {
    private val PATIENCE =
        "Some important data is being collected now.\nPlease be patient...wait..."
    private var pbWaiting: ProgressBar? = null
    private var tvTopCaption: TextView? = null
    private var etInput: EditText? = null
    private var btnExecute: Button? = null
    private var globalValue = 0
    private var accum = 0
    private var startTime: Long = 0
    private var handler: Handler? = null
    private var fgRunnable: Runnable? = null
    private var bgRunnable: Runnable? = null
    private var testThread: Thread? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex2)
        findViewByIds()
        initVariables()
        btnExecute!!.setOnClickListener {
            val input = etInput!!.text.toString()
            Toast.makeText(this@EX2, "You said: $input", Toast.LENGTH_SHORT).show()
        }

        //Start thread
        testThread!!.start()
        pbWaiting!!.incrementProgressBy(0)
    }

    fun findViewByIds() {
        println("11111111111111111")
        tvTopCaption = findViewById<View>(R.id.tv_top_caption) as TextView
        pbWaiting = findViewById<View>(R.id.pb_waiting) as ProgressBar
        etInput = findViewById<View>(R.id.et_input) as EditText
        btnExecute = findViewById<View>(R.id.btn_execute) as Button
    }

    fun initVariables() {
        println("2222222222222222")
        globalValue = 0
        accum = 0
        startTime = System.currentTimeMillis()
        handler = Handler()
        fgRunnable = object : Runnable {
            override fun run() {
                try {
                    // Calculate nev value
                    val progressStep = 5
                    val totalTime = ((System.currentTimeMillis() - startTime) / 1000).toDouble()
                    synchronized(this) { globalValue += 100 }
                    // Update UI
                    tvTopCaption!!.text = "$PATIENCE$totalTime - $globalValue"
                    pbWaiting!!.incrementProgressBy(progressStep)
                    accum += progressStep

                    // Check to stop
                    if (accum >= pbWaiting!!.max) {
                        tvTopCaption!!.text = getString(R.string.bg_work_is_over)
                        pbWaiting!!.visibility = View.GONE
                    }
                } catch (e: Exception) {
                    Log.e("fgRunnable", e.message!!)
                }
            }
        }
        bgRunnable = object : Runnable {
            override fun run() {
                try {
                    for (i in 0..19) {
                        // Sleep 1
                        Thread.sleep(1000)
                        // Nov talk to main thread
                        // Optionally change some global variable such as: globalValue
                        synchronized(this) { globalValue += 1 }
                        handler!!.post(fgRunnable as Runnable)
                    }
                } catch (e: Exception) {
                    Log.e("bgRunnable", e.message!!)
                }
            }
        }
        testThread = Thread(bgRunnable)
    }
}