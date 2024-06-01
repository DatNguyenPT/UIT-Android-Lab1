package com.example.myapplication

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button


class ButtonAdapter(context: Activity, layoutID: Int, objects: List<Button?>?) :
    ArrayAdapter<Button?>(context, layoutID, objects!!) {
    private val context: Context
    private val id: ArrayList<Int>? = null

    init {
        this.context = context
    }

    @SuppressLint("SuspiciousIndentation")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        if (convertView == null) {
            convertView =
                LayoutInflater.from(context).inflate(R.layout.menurecylerrow, parent, false)
        }
        val button = convertView!!.findViewById<View>(R.id.exOption) as Button
        if(position == 0){
            button.text = "EX 1 + 2"
        }else{
            button.text = "EX " + (position + 2)
        }
        button.setOnClickListener {
            // Determine which intent to start based on button position
            val intent: Intent
            intent = when (position) {
                0 -> Intent(context, EX1::class.java)
                1 -> Intent(context, EX3::class.java)
                2 -> Intent(context, EX4::class.java)
                else -> Intent(context, EX1::class.java)
            }
            context.startActivity(intent) // Start the intent
        }
        return convertView
    }
}