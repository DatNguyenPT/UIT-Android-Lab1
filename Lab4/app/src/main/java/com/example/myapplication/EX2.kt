package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EX2 : AppCompatActivity() {
    private lateinit var powerState: PowerState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ex2)
        initValue()
        val intentFilter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        registerReceiver(
            powerState,
            intentFilter
        )
    }

    private fun initValue() {
        powerState = PowerState()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(powerState)
    }

    inner class PowerState : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (context == null) return
            if (intent.action == Intent.ACTION_POWER_CONNECTED) {
                Toast.makeText(
                    context,
                    context.getString(R.string.power_connected),
                    Toast.LENGTH_LONG
                ).show()
            }
            if (intent.action == Intent.ACTION_POWER_DISCONNECTED) {
                Toast.makeText(
                    context,
                    context.getString(R.string.power_disconnected),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
