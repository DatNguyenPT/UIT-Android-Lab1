package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.util.concurrent.locks.ReentrantLock
import kotlin.properties.Delegates

public var isRunning: Boolean = false

class EX3 : AppCompatActivity() {
    private var reentrantLock: ReentrantLock? = null
    private var swAutoResponse: Switch? = null
    private var llButtons: LinearLayout? = null
    private var btnSafe: Button? = null
    private var btnMayday: Button? = null
    private var requesters: ArrayList<String>? = null
    private var adapter: ArrayAdapter<String>? = null
    private var lvMessages: ListView? = null
    private var broadcastReceiver: BroadcastReceiver? = null
    private var editor: SharedPreferences.Editor? = null
    private var sharedPreferences: SharedPreferences? = null
    private val AUTO_RESPONSE = "auto_response"

    private fun findViewsByIds() {
        swAutoResponse = findViewById(R.id.sw_auto_response)
        llButtons = findViewById(R.id.ll_buttons)
        lvMessages = findViewById(R.id.lv_messages)
        btnSafe = findViewById(R.id.btn_safe)
        btnMayday = findViewById(R.id.btn_mayday)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)
        findViewsByIds()
        initVariables()
        handleOnClickListener()
        val intent = intent
        if (intent.extras != null && intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY) != null) {
            processReceiveAddresses(intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY))
        }
    }

    private fun respond(to: String, response: String) {
        reentrantLock!!.lock()
        requesters!!.remove(to)
        adapter!!.notifyDataSetChanged()
        reentrantLock!!.unlock()
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(to, null, response, null, null)
    }

    private fun respond(ok: Boolean) {
        val okString = getString(R.string.i_am_safe_and_well_worry_not)
        val notOkString = getString(R.string.tell_my_mother_i_love_her)
        val outputString = if (ok) okString else notOkString
        val requestersCopy = requesters!!.clone() as ArrayList<String>
        for (to in requestersCopy) {
            respond(to, outputString)
        }
    }

    fun processReceiveAddresses(addresses: ArrayList<String>?) {
        for (i in addresses!!.indices) {
            if (!requesters!!.contains(addresses[i])) {
                reentrantLock!!.lock()
                requesters!!.add(addresses[i])
                adapter!!.notifyDataSetChanged()
                reentrantLock!!.unlock()
            }
            if (swAutoResponse!!.isChecked) respond(true)
        }
        Log.d("Requester", "requesters list size: ${requesters?.size}\n")
        Log.d("Requester", "requesters list: $requesters")
    }

    private fun handleOnClickListener() {
        // Handle onClickListener
        btnSafe!!.setOnClickListener { respond(true) }
        btnMayday!!.setOnClickListener { respond(false) }
        swAutoResponse!!.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                llButtons!!.visibility = View.GONE
            } else {
                llButtons!!.visibility = View.VISIBLE
            }
            // Save auto response setting
            editor?.putBoolean(AUTO_RESPONSE, isChecked)?.apply()
        }
    }

    private fun initBroadcastReceiver() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val addresses = intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY)
                processReceiveAddresses(addresses)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
        if (broadcastReceiver == null) initBroadcastReceiver()
        val intentFilter = IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        isRunning = false
        unregisterReceiver(broadcastReceiver)
    }

    private fun initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE)
        editor = sharedPreferences?.edit()
        reentrantLock = ReentrantLock()
        requesters = ArrayList()
        adapter = ArrayAdapter(this, R.layout.list_item, requesters!!)
        lvMessages!!.adapter = adapter
        val autoResponse = sharedPreferences?.getBoolean(AUTO_RESPONSE, false) ?: false
        swAutoResponse!!.isChecked = autoResponse
        if (autoResponse) llButtons!!.visibility = View.GONE
        initBroadcastReceiver()
    }
}
