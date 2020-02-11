package com.frogobox.cleaner.view.ui.fragment

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
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseFragment
import com.frogobox.cleaner.model.Apps
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.utils.Constant.Variable._CELCIUS
import com.frogobox.cleaner.view.adapter.CPUCoolerViewAdapter
import com.frogobox.cleaner.view.ui.activity.CPUScannerActivity
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_cpu_cooler.*
import kotlinx.android.synthetic.main.view_ads.*
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

class CPUCoolerFragment : BaseFragment() {

    private var check = 0

    private val batteryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            setupBatteryReceiver(intent)
        }
    }

    companion object {
        lateinit var apps: MutableList<Apps>
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_cpu_cooler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCheckCooledBattery()
        setupShowAdsBanner(ads_banner)
    }

    private fun setupCheckCooledBattery() {
        mActivity.registerReceiver(batteryReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        setupBatteryCooled()
    }

    private fun setupBatteryReceiver(intent: Intent) {
        val level = intent.getIntExtra("level", 0)
        val temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10
        tv_temperature_phone.text = "$temp°C"
        if (temp >= 30.0) {
            setupSmartPhoneHotCondition()
        }
    }

    private fun setupSmartPhoneHotCondition() {
        apps = mutableListOf()
        setupTextValueColor(tv_state_condition_1, getString(R.string.text_temp_condition_1_hot), colorTextRed())
        setupTextValueColor(tv_state_condition_2, getString(R.string.text_temp_condition_2_hot), colorTextRed())
        setupTextValueColor(tv_empty_apps_heat, "", colorTextRed())
        tv_temperature_phone.setTextColor(colorTextRed())
        iv_temperature_state.setImageResource(R.drawable.ic_temperature_hot_full)
        setOptimizeButton(btn_cooler, R.string.button_cool_down)
        btn_cooler.setOnClickListener { setupCoolingBattery() }
        setupHotApps()
    }

    private fun setupBatteryCooled() {
        setupTextValueColor(tv_state_condition_1, getString(R.string.text_temp_condition_1_cool), colorTextGreen())
        setupTextValueColor(tv_state_condition_2, getString(R.string.text_temp_condition_2_cool), colorTextGreen())
        setupTextValueColor(tv_empty_apps_heat, getString(R.string.text_cpu_cooler_empty_apps), colorTextGreen())
        tv_temperature_phone.setTextColor(mActivity.getColorRes(R.color.colorPrimaryText))
        iv_temperature_state.setImageResource(R.drawable.ic_temperature_cold_full)
        setDoneOptimizeButton(btn_cooler, R.string.button_cooled)
        btn_cooler.setOnClickListener { showCustomToast(getString(R.string.toast_cpu_normal_temperature)) }
    }

    private fun setupCoolingBattery() {
        startActivity(Intent(context, CPUScannerActivity::class.java))
        Handler().postDelayed({
            setupBatteryCooled()
            tv_temperature_phone.text = "25.3" + _CELCIUS
            recycler_view.adapter = null
        }, 2000)
    }

    private fun setupHotApps() {

        val pm = activity?.packageManager
        val packages = pm?.getInstalledApplications(PackageManager.GET_META_DATA)

        if (packages != null) {
            for (k in packages.indices) {
                val packageName = packages[k].packageName
                Log.e("packageName-->", "" + packageName)

                if (packageName != Constant.Variable.PACKAGE_NAME) {
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
            val adapter = CPUCoolerViewAdapter(apps)
            adapter.notifyDataSetChanged()
            setupRecyclerView(adapter)
        }
    }

    private fun setupRecyclerView(adapter: CPUCoolerViewAdapter) {
        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.itemAnimator!!.addDuration = 10000
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        mActivity.unregisterReceiver(batteryReceiver)
    }

}