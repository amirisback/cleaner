package com.frogobox.cleaner.mvvm.scan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.databinding.ItemCpuCoolerDoingBinding
import com.frogobox.cleaner.model.Apps

/**
 * Created by Frogobox Software Industries 2/25/2017.
 */

// Get List of Apps Causing Junk Files
class ScanCpuAppAdapter(private val apps: List<Apps>) :
        RecyclerView.Adapter<ScanCpuAppAdapter.ScanCpuAppHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScanCpuAppHolder {
        return ScanCpuAppHolder(
            ItemCpuCoolerDoingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ScanCpuAppHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class ScanCpuAppHolder(private val binding: ItemCpuCoolerDoingBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Apps) {
            binding.appimage.setImageDrawable(data.image)
        }
    }
}
