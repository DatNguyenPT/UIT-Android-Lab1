package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Random


class EX1 : AppCompatActivity() {
    private var pbFirst: ProgressBar? = null
    private var pbSecond: ProgressBar? = null
    private var tvMsgWorking: TextView? = null
    private var tvMsgReturned: TextView? = null
    private var isRunning = false
    private var MAX_SEC = 0
    private var intTest = 0
    private var bgThread: Thread? = null
    private var handler: Handler? = null
    private var btnStart: Button? = null
    private fun findViewByIds() {
        pbFirst = findViewById<View>(R.id.pb_first) as ProgressBar
        pbSecond = findViewById<View>(R.id.pb_second) as ProgressBar
        tvMsgWorking = findViewById<View>(R.id.tv_working) as TextView
        tvMsgReturned = findViewById<View>(R.id.tv_return) as TextView
        btnStart = findViewById<View>(R.id.btn_start) as Button
    }

    private fun initVariables() {
        isRunning = false
        MAX_SEC = 20
        intTest = 1
        pbFirst!!.max = MAX_SEC
        pbFirst!!.progress = 0

        // Init Views
        pbFirst!!.visibility = View.GONE
        pbSecond!!.visibility = View.GONE
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                val returnedValue = msg.obj as String
                // Don something vith the value sent by background thread here...
                tvMsgReturned!!.text = getString(R.string.returned_by_bg_thread) + returnedValue
                pbFirst!!.incrementProgressBy(2)
                // Testing thread's termination
                if (pbFirst!!.progress == MAX_SEC) {
                    println("Done")
                    tvMsgReturned!!.text =
                        getString(R.string.done_background_thread_has_been_stopped)
                    tvMsgWorking!!.text = getString(R.string.done)
                    pbFirst!!.visibility = View.GONE
                    pbFirst!!.progress = 0
                    pbSecond!!.visibility = View.GONE
                    btnStart!!.visibility = View.VISIBLE
                    isRunning = false
                } else {
                    tvMsgWorking!!.text = getString(R.string.working) + pbFirst!!.progress
                }
            }
        }
    }

    private fun initBgThread() {
        bgThread = Thread {
            try {
                var i = 0
                while (i < MAX_SEC && isRunning) {

                    // Sleep one second
                    Thread.sleep(1000)
                    val rnd = Random()

                    // This is a locally generated value
                    var data = "Thread value: " + rnd.nextInt(101)

                    // We can see change (global) class variables
                    data += getString(R.string.global_value_seen) + " " + intTest
                    intTest++

                    // If thread is still alive send the message
                    if (isRunning) {
                        // Request a message token and put some data in it
                        val msg = handler!!.obtainMessage(1, data)
                        handler!!.sendMessage(msg)
                    }
                    i++
                }
            } catch (t: Throwable) {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex1)
        findViewByIds()
        initVariables()


        // Hand. clickListenner
        btnStart!!.setOnClickListener {
            isRunning = true
            pbFirst!!.visibility = View.VISIBLE
            pbSecond!!.visibility = View.VISIBLE
            btnStart!!.visibility = View.GONE
            bgThread!!.start()
        }
    }

    override fun onStart() {
        super.onStart()
        initBgThread()
    }

    override fun onStop() {
        super.onStop()
    }
}