package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView



class Adapter : RecyclerView.Adapter<PagerVH>() {

    private val colors = intArrayOf(
        android.R.color.black,
        android.R.color.holo_red_light,
        android.R.color.holo_blue_dark,
        android.R.color.holo_purple
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH =
        PagerVH(LayoutInflater.from(parent.context).inflate(R.layout.fragment, parent, false))

    override fun getItemCount(): Int = 2 // number of pages

    override fun onBindViewHolder(holder: PagerVH, position: Int) {
        val currentItemPosition = position % colors.size
        holder.itemView.apply {
            findViewById<TextView>(R.id.name).setBackgroundResource(R.color.light_green)
            when (currentItemPosition) {
                0 -> {
                    findViewById<TextView>(R.id.name).text = context.getString(R.string.elizabeth)
                    findViewById<TextView>(R.id.position).text = context.getString(R.string.pm)
                    findViewById<TextView>(R.id.email).text = context.getString(R.string.eli_email)
                    findViewById<LinearLayout>(R.id.background).setBackgroundResource(R.color.light_pink)
                }
                1 -> {
                    findViewById<TextView>(R.id.name).text = context.getString(R.string.catherine)
                    findViewById<TextView>(R.id.position).text = context.getString(R.string.sale)
                    findViewById<TextView>(R.id.email).text = context.getString(R.string.cat_email)
                    findViewById<LinearLayout>(R.id.background).setBackgroundResource(R.color.light_blue)
                }
                else ->{

                }
            }
        }
    }
}

class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)

