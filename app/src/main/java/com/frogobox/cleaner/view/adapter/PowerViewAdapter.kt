package com.frogobox.cleaner.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.model.PowerItem
import kotlinx.android.synthetic.main.content_item_power.view.*

/**
 * Created by Frogobox Software Industries 2/16/2017.
 */

class PowerViewAdapter(private val apps: List<PowerItem>) :
        RecyclerView.Adapter<PowerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.content_item_power, parent, false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (text) = apps[position]
        holder.size.text = text
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val size = view.items
    }

}
