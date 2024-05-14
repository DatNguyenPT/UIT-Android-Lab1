package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EX1 : AppCompatActivity() {
    private var broadcastReceiver: BroadcastReceiver? = null
    private var intentFilter: IntentFilter? = null

    private fun processReceive(context: Context, intent: Intent) {
        Toast.makeText(context, getString(R.string.you_have_a_new_mesage), Toast.LENGTH_LONG).show()
        val tvContent = findViewById<TextView>(R.id.tv_content)
        val SMS_EXTRA = "pdus"
        val bundle = intent.extras
        val messages = bundle!![SMS_EXTRA] as Array<Any>?
        var sms = ""
        var smsMsg: SmsMessage
        for (i in messages!!.indices) {
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
}