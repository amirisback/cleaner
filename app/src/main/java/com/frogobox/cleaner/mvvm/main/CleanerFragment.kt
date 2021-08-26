package com.frogobox.cleaner.mvvm.main

import android.app.ActivityManager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ACTIVITY_SERVICE
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.frogobox.cleaner.R
import com.frogobox.cleaner.core.BaseFragment
import com.frogobox.cleaner.databinding.FragmentChargeBoosterBinding
import com.frogobox.cleaner.service.CleanerBroadcastReceiver
import com.frogobox.cleaner.utils.Constant.SHARED_PREF_BOOSTER
import com.frogobox.cleaner.utils.Constant.SHARED_PREF_VALUE
import com.frogobox.cleaner.utils.Constant.SHARED_PREF_WASEEM
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import java.io.RandomAccessFile
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.math.abs

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

class CleanerFragment : BaseFragment<FragmentChargeBoosterBinding>() {

    private var x: Int = 0
    private var y: Int = 0
    private var counter = 0

    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun setupUI(savedInstanceState: Bundle?) {

        sharedpreferences = mActivity.getSharedPreferences(SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
        editor = sharedpreferences.edit()

        setupCheckOptimize()
        setupButtonOptimize()
        setupCheckStartUp()
    }

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentChargeBoosterBinding {
        return FragmentChargeBoosterBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
    }

    private fun setupOptimizingPhone() {

        val rotate = RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 5000
        rotate.interpolator = LinearInterpolator()
        binding.ivBackLineSpeedo?.startAnimation(rotate)

        setupInitDynamicView()

        binding.dynamicArcView2?.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(2000)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {
                        dynamicOptimizingEventStart()
                    }

                    override fun onEventEnd(decoEvent: DecoEvent) {}
                }).build())

        binding.dynamicArcView2?.addEvent(DecoEvent.Builder(25f).setIndex(setupInitDynamicSeriesIndex()).setDelay(4000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {
                dynamicOptimizingEventStart()
            }

            override fun onEventEnd(decoEvent: DecoEvent) {
                dynamicOptimzingEventEnd()
            }
        }).build())

        val animation = TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f)          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.duration = 5000  // animation duration
        animation.repeatCount = 0
        animation.interpolator = LinearInterpolator()// animation repeat count
        animation.fillAfter = true
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                animationOptimizeStart()
            }

            override fun onAnimationEnd(animation: Animation) {
                animationOptimizeEnd()
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })

        binding.ivWaves?.startAnimation(animation)
    }

    private fun setupCheckStartUp() {
        val proc = Random().nextInt(60) + 30
        val timerCounting = Timer()
        val timerTaskCounting = object : TimerTask() {
            override fun run() {
                try {
                    mActivity.runOnUiThread {
                        counter++
                        binding.tvRamsize?.text = counter.toString() + "MB"
                    }

                } catch (e: Exception) {
                }
            }

        }
        timerCounting.schedule(timerTaskCounting, 30, 30)

        setupInitDynamicView()

        binding.dynamicArcView2?.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(600)
                .build())

        binding.dynamicArcView2?.addEvent(DecoEvent.Builder(proc.toFloat()).setIndex(setupInitDynamicSeriesIndex()).setDelay(2000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {}
            override fun onEventEnd(decoEvent: DecoEvent) {
                dynamicStartUpEventEnd(timerCounting, timerTaskCounting)
            }
        }).build())

        y = Random().nextInt(50) + 15

        binding.apply {
            tvPrecentageProceses.text = y.toString() + ""
            tvTotalRam.text = getTotalRam()
            tvUsedRam.text = "${getUsedMemorySize()} MB/ "
            tvAppsFreed.text = getTotalRam()
            tvAppsUsed.text = (getUsedMemorySize() - x.toLong() - 30).toString() + " MB/ "
        }

    }

    private fun setupCheckOptimize() {
        binding.apply {
            if (sharedpreferences.getString(SHARED_PREF_BOOSTER, "1") == "0") {
                setDoneOptimizeButton(optbutton, R.string.button_optimized)
                tvRamsize.text = sharedpreferences.getString(SHARED_PREF_VALUE, "50MB")
            } else {
                setOptimizeButton(optbutton, R.string.button_optimize)
                tvPrecentageRam.text = (Random().nextInt(60) + 40).toString() + "%"
            }
        }

    }

    private fun setupAlarmManager() {
        val intent = Intent(mActivity, CleanerBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(mActivity, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = mActivity.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100 * 1000, pendingIntent)

    }

    private fun setupButtonOptimize() {
        binding.optbutton.setOnClickListener {
            if (sharedpreferences.getString(SHARED_PREF_BOOSTER, "1") == "1") {
                editor.putString(SHARED_PREF_BOOSTER, "0")
                editor.apply()
                setupOptimizingPhone()
                setupAlarmManager()
            } else {
                showCustomToast(getString(R.string.toast_phone_already_optimized))
            }
        }
    }

    private fun setupInitDynamicView() {

        binding.apply {
            dynamicArcView2.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                    .setRange(0f, 100f, 0f)
                    .setInterpolator(AccelerateInterpolator())
                    .build())

            dynamicArcView2.addSeries(SeriesItem.Builder(colorAccent())
                    .setRange(0f, 100f, 100f)
                    .setInitialVisibility(false)
                    .setLineWidth(32f)
                    .build())
        }

    }

    private fun setupInitDynamicSeriesIndex(): Int {
        val seriesItem2 = SeriesItem.Builder(colorPrimary())
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .build()

        return binding.dynamicArcView2!!.addSeries(seriesItem2)
    }

    private fun dynamicOptimizingEventStart() {
        binding.apply {
            tvFound.text = ""
            tvStorage.text = ""
            tvRamsize.text = "Optimizing..."
        }

    }

    private fun dynamicOptimzingEventEnd() {
        setupShowAdsInterstitial()
        binding.apply {
            tvFound.text = "Found"
            tvStorage.text = "Storage"
            tvPrecentageRam.text = (Random().nextInt(40) + 20).toString() + "%"

        }
    }

    private fun dynamicStartUpEventEnd(timerCounting: Timer, TimerTaskCounting: TimerTask) {
        timerCounting.cancel()
        TimerTaskCounting.cancel()
        timerCounting.purge()

        binding.tvRamsize.text = "${getUsedMemorySize()} MB"

        if (sharedpreferences.getString(SHARED_PREF_BOOSTER, "1") == "0") {
            binding.tvRamsize.text = sharedpreferences.getString(SHARED_PREF_VALUE, "50MB")
        }

        val timerCounting2 = Timer()
        val timerTask2 = object : TimerTask() {
            override fun run() {
                try {
                    mActivity.runOnUiThread {
                        binding.tvRamsize.text = "${getUsedMemorySize()} MB"
                        if (sharedpreferences.getString(SHARED_PREF_BOOSTER, "1") == "0") {
                            binding.tvRamsize.text = sharedpreferences.getString(SHARED_PREF_VALUE, "50MB")
                        }
                        timerCounting2.cancel()
//                        timerTask2.cancel()
                        timerCounting2.purge()
                    }
                } catch (e: Exception) {
                }
            }
        }

        timerCounting2.schedule(timerTask2, 100, 100)
    }

    private fun getTotalRam(): String {
        val twoDecimalForm = DecimalFormat("#.##")
        val reader = RandomAccessFile("/proc/meminfo", "r")
        val load = reader.readLine()
        val p = Pattern.compile("(\\d+)")
        val m = p.matcher(load)

        var value = ""

        while (m.find()) {
            value = m.group(1)
        }

        reader.close()

        val totRam = java.lang.Double.parseDouble(value)
        val mb = totRam / 1024.0
        val gb = totRam / 1048576.0
        val tb = totRam / 1073741824.0

        return when {
            tb > 1 -> twoDecimalForm.format(tb) + " TB"
            gb > 1 -> twoDecimalForm.format(gb) + " GB"
            mb > 1 -> twoDecimalForm.format(mb) + " MB"
            else -> twoDecimalForm.format(totRam) + " KB"
        }
    }

    private fun getUsedMemorySize(): Long {
        try {
            val mi = ActivityManager.MemoryInfo()
            val mActivityManager = mActivity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
            mActivityManager.getMemoryInfo(mi)
            return mi.availMem / 1048576L
        } catch (e: Exception) {
            return 200
        }
    }

    private fun animationOptimizeStart() {
        binding.apply {
            tvScanning.visibility = View.VISIBLE
            optbutton.visibility = View.INVISIBLE
            tvScanning.text = "SCANNING..."
        }
    }

    private fun animationOptimizeEnd() {
        editor.putString(SHARED_PREF_VALUE, (getUsedMemorySize() - x).toString() + " MB")
        editor.apply()

        x = Random().nextInt(100) + 30
        val proc = Random().nextInt(10) + 5

        binding.apply {
            tvScanning.visibility = View.INVISIBLE
            optbutton.visibility = View.VISIBLE
            setDoneOptimizeButton(optbutton, R.string.button_optimized)

            tvTotalRam.text = getTotalRam()
            tvUsedRam.text = (getUsedMemorySize() - x).toString() + " MB/ "
            tvAppsFreed.text = getTotalRam()
            tvAppsUsed.text = abs(getUsedMemorySize() - x.toLong() - 30).toString() + " MB/ "
            tvPrecentageProceses.text = (y - proc).toString()
            tvRamsize.text = (getUsedMemorySize() - x).toString() + " MB"
        }
    }

}