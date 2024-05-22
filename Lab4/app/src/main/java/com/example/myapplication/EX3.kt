package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.content.pm.PackageManager
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
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.Locale
import java.util.concurrent.locks.ReentrantLock
import kotlin.properties.Delegates

public var isRunning by Delegates.notNull<Boolean>()

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
    private val REQUEST_SEND_SMS = 123
    private lateinit var intentFilter: IntentFilter

    private fun findViewsByIds() {
        swAutoResponse = findViewById<View>(R.id.sw_auto_response) as Switch
        llButtons = findViewById<View>(R.id.ll_buttons) as LinearLayout
        lvMessages = findViewById<View>(R.id.lv_messages) as ListView
        btnSafe = findViewById<View>(R.id.btn_safe) as Button
        btnMayday = findViewById<View>(R.id.btn_mayday) as Button
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex3)
        findViewsByIds()
        initVariables()
        handleOnClickListenner()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            registerReceiver(broadcastReceiver, intentFilter, RECEIVER_EXPORTED)
        }else {
            registerReceiver(broadcastReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
        }
        // Check and request SEND_SMS permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), REQUEST_SEND_SMS)
        }

        val intent = intent
        if (intent.extras != null && intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY) != null) {
            processReceiveAddresses(intent.getStringArrayListExtra(SmsReceiver.SMS_MESSAGE_ADDRESS_KEY))
        }
        requesters.add("+84352829348")
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
        btnSafe.setOnClickListener { respond(true) }
        btnMayday.setOnClickListener { respond(false) }
        swAutoResponse.setOnCheckedChangeListener { buttonView, isChecked ->
            llButtons.visibility = if (isChecked) View.GONE else View.VISIBLE
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
        intentFilter = IntentFilter(SmsReceiver.SMS_FORWARD_BROADCAST_RECEIVER)
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
        if (!this::broadcastReceiver.isInitialized) initBroadcastReceiver()
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_SEND_SMS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted, nothing special to do
                } else {
                    // Permission denied, inform the user
                    // You can show a Toast or Snackbar to notify the user
                    // Example:
                    Toast.makeText(this, "SMS permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
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
                        if (isRunning) {
                            val iForwardBroadcastReceiver = Intent(SMS_FORWARD_BROADCAST_RECEIVER)
                            iForwardBroadcastReceiver.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses)
                            context.sendBroadcast(iForwardBroadcastReceiver)
                        } else {
                            val intentStartActivity = Intent(context, EX3::class.java)
                            intentStartActivity.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            intentStartActivity.putStringArrayListExtra(SMS_MESSAGE_ADDRESS_KEY, addresses)
                            context.startActivity(intentStartActivity)
                        }
                    }
                }
            }
        }
    }
}
