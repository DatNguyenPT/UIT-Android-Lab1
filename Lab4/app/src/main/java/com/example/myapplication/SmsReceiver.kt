package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import java.util.Locale

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