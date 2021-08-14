package com.frogobox.cleaner.mvvm.cpu

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.databinding.ItemCpuCoolerBinding
import com.frogobox.cleaner.model.Apps

/**
 * Created by Frogobox Software Industries 2/16/2017.
 */

class CpuCoolerAdapter(private val apps: List<Apps>) :
    RecyclerView.Adapter<CpuCoolerAdapter.CpuCoolerHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CpuCoolerHolder {
        return CpuCoolerHolder(
            ItemCpuCoolerBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CpuCoolerHolder, position: Int) {
        holder.bind(apps[position])
    }

    override fun getItemCount(): Int {
        return apps.size
    }

    inner class CpuCoolerHolder(private val binding: ItemCpuCoolerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Apps) {
            binding.appimage.setImageDrawable(data.image)
        }
    }
}
