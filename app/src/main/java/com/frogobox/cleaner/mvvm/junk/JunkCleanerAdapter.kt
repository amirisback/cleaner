package com.frogobox.cleaner.mvvm.junk

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.databinding.ItemJunkAppsBinding
import com.frogobox.cleaner.model.Apps
/**
 * Created by Frogobox Software Industries 2/25/2017.
 */

class JunkCleanerAdapter(private val apps: List<Apps>) : RecyclerView.Adapter<JunkCleanerAdapter.JunkCleanerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JunkCleanerHolder {
        return JunkCleanerHolder(
            ItemJunkAppsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JunkCleanerHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class JunkCleanerHolder(private val binding: ItemJunkAppsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Apps) {
            binding.apply {
                apptext.text = data.size
                appimage.setImageDrawable(data.image)
            }
        }
    }

}
