package com.example.myapplication;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.telephony.SmsManager
import android.telephony.SmsMessage
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale
import java.util.concurrent.locks.ReentrantLock

class EX3 : AppCompatActivity() {

    private lateinit var reentrantLock: ReentrantLock
    private lateinit var swAutoResponse: Switch
    private lateinit var llButtons: LinearLayout
    private lateinit var btnSafe: Button
    private lateinit var btnMayday: Button
    private lateinit var requesters: ArrayList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var lvMessages: ListView
    private lateinit var broadcastReceiver: BroadcastReceiver
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var sharedPreferences: SharedPreferences
    private val AUTO_RESPONSE = "auto_response"

    private fun findViewsByIds() {
        swAutoResponse = findViewById<View>(R.id.sw_auto_response) as Switch
        llButtons = findViewById<View>(R.id.ll_buttons) as LinearLayout
        lvMessages = findViewById<View>(R.id.lv_messages) as ListView
        btnSafe = findViewById<View>(R.id.btn_safe) as Button
        btnMayday = findViewById<View>(R.id.btn_mayday) as Button
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)
        findViewsByIds()
        initVariables()
        handleOnClickListenner()
        val intent = intent
        if (intent.extras != null && intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY) != null) {
            processReceiveAddresses(intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY))
        }
        requesters.add("+84938758919")
    }

    private fun respond(to: String, response: String) {
        reentrantLock.lock()
        requesters.remove(to)
        adapter.notifyDataSetChanged()
        reentrantLock.unlock()
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(to, null, response, null, null)
    }

    private fun respond(ok: Boolean) {
        val okString = getString(R.string.i_am_safe_and_well_worry_not)
        val notOkString = getString(R.string.tell_my_mother_i_love_her)
        val outputString = if (ok) okString else notOkString
        val requestersCopy = ArrayList(requesters)
        for (to in requestersCopy) {
            respond(to, outputString)
        }
    }

    private fun processReceiveAddresses(addresses: ArrayList<String>?) {
        addresses?.let {
            for (address in it) {
                if (!requesters.contains(address)) {
                    reentrantLock.lock()
                    requesters.add(address)
                    adapter.notifyDataSetChanged()
                    reentrantLock.unlock()
                }
                if (swAutoResponse.isChecked) respond(true)
            }
        }
    }

    private fun handleOnClickListenner() {
        // Handle onClickListener
        btnSafe.setOnClickListener { respond(true) }
        btnMayday.setOnClickListener { respond(false) }
        swAutoResponse.setOnCheckedChangeListener { buttonView, isChecked ->
            llButtons.visibility = if (isChecked) View.GONE else View.VISIBLE
            // Save auto response setting
            editor.putBoolean(AUTO_RESPONSE, isChecked)
            editor.apply()
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
        if (!this::broadcastReceiver.isInitialized) initBroadcastReceiver()
        val intentFilter = IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER)
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    private fun initVariables() {
        sharedPreferences = getPreferences(MODE_PRIVATE)
        editor = sharedPreferences.edit()
        reentrantLock = ReentrantLock()
        requesters = ArrayList()
        adapter = ArrayAdapter(this, R.layout.list_item, requesters)
        lvMessages.adapter = adapter
        val autoResponse = sharedPreferences.getBoolean(AUTO_RESPONSE, false)
        swAutoResponse.isChecked = autoResponse
        llButtons.visibility = if (autoResponse) View.GONE else View.VISIBLE
        initBroadcastReceiver()
    }

    class SmsReceiver : BroadcastReceiver() {
        companion object {
            const val SMS_FORWARD_BROADCAST_RECEIVER = "sms_forward_broadcast_receiver"
            const val SMS_MESSAGE_ADDRESS_KEY = "sms_messages_key"
        }

        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
                val queryString = context.getString(R.string.are_you_ok).lowercase(Locale.getDefault())
                val bundle = intent.extras
                if (bundle != null) {
                    val pdus = bundle["pdus"] as Array<Any>?
                    val messages = arrayOfNulls<SmsMessage>(pdus!!.size)
                    val format = bundle.getString("format")
                    for (i in pdus.indices) {
                        messages[i] = if (Build.VERSION.SDK_INT >= 23) {
                            SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
                        } else {
                            SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        }
                    }
                    val addresses = ArrayList<String?>()
                    for (msg in messages) {
                        if (msg!!.messageBody.lowercase(Locale.getDefault()).contains(queryString)) {
                            addresses.add(msg.originatingAddress)
                        }
                    }
                    if (addresses.isNotEmpty()) {
                        val iForwardBroadcastReceiver = Intent(SMS_FORWARD_BROADCAST_RECEIVER)
                        iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses)
                        context.sendBroadcast(iForwardBroadcastReceiver)
                    }
                }
            }
        }
    }
}
