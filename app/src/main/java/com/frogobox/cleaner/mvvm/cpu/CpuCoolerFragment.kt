package com.frogobox.cleaner.mvvm.cpu

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.BatteryManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.core.BaseFragment
import com.frogobox.cleaner.databinding.FragmentCpuCoolerBinding
import com.frogobox.cleaner.model.Apps
import com.frogobox.cleaner.mvvm.scan.ScanCpuAppActivity
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.utils.Constant._CELCIUS
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import java.io.File

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.base
 */

class CpuCoolerFragment : BaseFragment<FragmentCpuCoolerBinding>() {

    private var check = 0

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setupBatteryReceiver(intent)
        }
    }

    companion object {
        lateinit var apps: MutableList<Apps>
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupCheckCooledBattery()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCpuCoolerBinding {
        return FragmentCpuCoolerBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
    }
    
    private fun setupCheckCooledBattery() {
        mActivity.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        setupBatteryCooled()
    }

    private fun setupBatteryReceiver(intent: Intent) {
        val level = intent.getIntExtra("level", 0)
        val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10
        binding.tvTemperaturePhone.text = "$temp°C"
        if (temp >= 30.0) {
            setupSmartPhoneHotCondition()
        }
    }

    private fun setupSmartPhoneHotCondition() {
        apps = mutableListOf()
        binding.apply {
            setupTextValueColor(tvStateCondition1, getString(R.string.text_temp_condition_1_hot), colorTextRed())
            setupTextValueColor(tvStateCondition2, getString(R.string.text_temp_condition_2_hot), colorTextRed())
            setupTextValueColor(tvEmptyAppsHeat, "", colorTextRed())
            tvTemperaturePhone.setTextColor(colorTextRed())
            ivTemperatureState.setImageResource(R.drawable.ic_temperature_hot_full)
            setOptimizeButton(btnCooler, R.string.button_cool_down)
            btnCooler.setOnClickListener { setupCoolingBattery() }
        }
        setupHotApps()
    }

    private fun setupBatteryCooled() {
        binding.apply {
            setupTextValueColor(tvStateCondition1, getString(R.string.text_temp_condition_1_cool), colorTextGreen())
            setupTextValueColor(tvStateCondition2, getString(R.string.text_temp_condition_2_cool), colorTextGreen())
            setupTextValueColor(tvEmptyAppsHeat, getString(R.string.text_cpu_cooler_empty_apps), colorTextGreen())
            tvTemperaturePhone.setTextColor(mActivity.getColorRes(R.color.colorPrimaryText))
            ivTemperatureState.setImageResource(R.drawable.ic_temperature_cold_full)
            setDoneOptimizeButton(btnCooler, R.string.button_cooled)
            btnCooler.setOnClickListener { showCustomToast(getString(R.string.toast_cpu_normal_temperature)) }
        }
    }

    private fun setupCoolingBattery() {
        setupShowAdsInterstitial()
        startActivity(Intent(context, ScanCpuAppActivity::class.java))
        Handler().postDelayed({
            setupBatteryCooled()
            binding.apply {
                tvTemperaturePhone.text = "25.3$_CELCIUS"
                recyclerView.adapter = null
            }

        }, 2000)
    }

    private fun setupHotApps() {

        val pm = activity?.packageManager
        val packages = pm?.getInstalledApplications(PackageManager.GET_META_DATA)

        if (packages != null) {
            for (k in packages.indices) {
                val packageName = packages[k].packageName
                Log.e("packageName-->", "" + packageName)

                if (packageName != Constant.PACKAGE_NAME) {
                    var ico: Drawable? = null
                    try {
                        val pName = pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)) as String
                        val app = Apps()
                        val file = File(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA).publicSourceDir)
                        val size = file.length()
                        Log.e("SIZE", (size / 1000000).toString() + "")
                        app.size = (size / 1000000 + 20).toString() + "MB"
                        val a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
                        ico = activity?.packageManager?.getApplicationIcon(packages[k].packageName)
                        app.image = ico
                        activity?.packageManager
                        Log.e("ico-->", "" + ico)

                        if (a.flags and ApplicationInfo.FLAG_SYSTEM == 0) {
                            if (check <= 5) {
                                check++
                                apps.add(app)
                            } else {
                                mActivity.unregisterReceiver(batteryReceiver)
                                break
                            }

                        }

                    } catch (e: PackageManager.NameNotFoundException) {
                        Log.e("ERROR", "Unable to find icon for package '"
                                + packageName + "': " + e.message)
                    }

                }
            }

        }

        if (apps.size > 1) {
            val adapter = CpuCoolerAdapter(apps)
            adapter.notifyDataSetChanged()
            setupRecyclerView(adapter)
        }
    }

    private fun setupRecyclerView(adapterCooler: CpuCoolerAdapter) {
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.apply {
            itemAnimator = SlideInLeftAnimator()
            itemAnimator!!.addDuration = 10000
            layoutManager = mLayoutManager
            itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
            computeHorizontalScrollExtent()
            adapter = adapterCooler

        }
    }

}