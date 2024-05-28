package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast


class PowerStateChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (context == null) return
        if (intent.action == Intent.ACTION_POWER_CONNECTED) {
            Toast.makeText(context, "Power Connected", Toast.LENGTH_LONG).show()
        }
        if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
            Toast.makeText(context, "Power Disconnected", Toast.LENGTH_LONG).show()
        }
    }
}