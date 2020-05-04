package com.frogobox.cleaner.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.model.Apps
import kotlinx.android.synthetic.main.list_cpu_cooler.view.*

/**
 * Created by Frogobox Software Industries 2/16/2017.
 */

class CPUCoolerViewAdapter(private val apps: List<Apps>)
    : RecyclerView.Adapter<CPUCoolerViewAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_cpu_cooler, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (size, image) = apps[position]
        holder.image.setImageDrawable(image)
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.appimage
    }
}
