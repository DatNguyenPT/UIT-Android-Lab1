package com.example.myapplication

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class EX1 : AppCompatActivity() {
    private var broadcastReceiver: BroadcastReceiver? = null
    private var intentFilter: IntentFilter? = null
    private val PERMISSION_REQUEST_CODE = 1

    private fun checkAndRequestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            ), PERMISSION_REQUEST_CODE)
        }
    }

    private fun processReceive(context: Context, intent: Intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_mesage), Toast.LENGTH_LONG).show()
        val tvContent = findViewById<TextView>(R.id.tv_content)
        val SMS_EXTRA = "pdus"
        val bundle = intent.extras
        if (bundle != null) {
            val messages = bundle[SMS_EXTRA] as Array<Any>?
            if (messages != null) {
                var sms = ""
                var smsMsg: SmsMessage
                for (i in messages.indices) {
                    smsMsg = if (Build.VERSION.SDK_INT >= 23) {
                        val format = bundle.getString("format")
                        SmsMessage.createFromPdu(messages[i] as ByteArray, format)
                    } else {
                        SmsMessage.createFromPdu(messages[i] as ByteArray)
                    }
                    val smsBody = smsMsg.messageBody
                    val address = smsMsg.displayOriginatingAddress
                    sms += "$address:\n$smsBody\n"
                }
                tvContent.text = sms
            } else {
                Log.e("EX1", "No SMS messages received.")
            }
        } else {
            Log.e("EX1", "No bundle received.")
        }
    }

    private fun initBroadcastReceiver() {
        intentFilter = IntentFilter("android.provider.Telephony.SMS_RECEIVED")
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                processReceive(context, intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex1)
        checkAndRequestPermissions()
        initBroadcastReceiver()
    }

    override fun onResume() {
        super.onResume()
        if (broadcastReceiver == null) {
            initBroadcastReceiver()
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcastReceiver)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // Permission granted
                } else {
                    // Permission denied, notify the user
                    Toast.makeText(this, "SMS permission denied. Cannot receive messages.", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }
}
