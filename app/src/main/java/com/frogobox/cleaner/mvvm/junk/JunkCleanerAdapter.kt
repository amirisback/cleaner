package com.frogobox.cleaner.mvvm.junk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.model.Apps
import kotlinx.android.synthetic.main.list_junk_apps.view.*

/**
 * Created by Frogobox Software Industries 2/25/2017.
 */

class JunkCleanerAdapter(private val apps: List<Apps>) : RecyclerView.Adapter<JunkCleanerAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_junk_apps, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val (size, image) = apps[position]
        holder.size.text = size
        holder.image.setImageDrawable(image)
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val size = view.apptext
        val image = view.appimage
    }

}
