package com.frogobox.cleaner.view.fragment

import android.content.*
import android.graphics.Color
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseFragment
import com.frogobox.cleaner.model.Time
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.activity.batterysaver.NormalModeBatterySaverActivity
import com.frogobox.cleaner.view.activity.batterysaver.PowerSavingBatterySaverActivity
import com.frogobox.cleaner.view.activity.batterysaver.UltraBaterrySaverActivity
import kotlinx.android.synthetic.main.fragment_battery_saver.*
import me.itangqi.waveloadingview.WaveLoadingView

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
 *
 */

class BatterySaverFragment : BaseFragment() {

    private var sharedpreferences: SharedPreferences? = null
    private val editor: SharedPreferences.Editor? = null

    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            waveView.progressValue = level
            waveView.centerTitle = "$level%"
            //            mWaveLoadingView.setBottomTitle(level+"%");
            setupCheckBatteryLevel(level)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_battery_saver, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedpreferences = activity!!.getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE)
        activity!!.registerReceiver(this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        try {

            powersaving.setOnClickListener {
                val i = Intent(activity, PowerSavingBatterySaverActivity::class.java)
                i.putExtra(Constant.Variable.EXTRA_HOURS, hourp!!.text)
                i.putExtra(Constant.Variable.EXTRA_MINUTES, minutesp!!.text)
                i.putExtra(Constant.Variable.EXTRA_MINUTES_NORMAL, minutes!!.text)
                i.putExtra(Constant.Variable.EXTRA_HOURS_NORMAL, hourn!!.text)
                startActivity(i)
            }

            ultra.setOnClickListener {
                val i = Intent(activity, UltraBaterrySaverActivity::class.java)
                i.putExtra(Constant.Variable.EXTRA_HOURS, houru!!.text)
                i.putExtra(Constant.Variable.EXTRA_MINUTES, minutesu!!.text)
                i.putExtra(Constant.Variable.EXTRA_MINUTES_NORMAL, minutes!!.text)
                i.putExtra(Constant.Variable.EXTRA_HOURS_NORMAL, hourn!!.text)
                startActivity(i)
            }

            normal.setOnClickListener {
                baseStartActivity<NormalModeBatterySaverActivity>()
            }

            waveView.setShapeType(WaveLoadingView.ShapeType.CIRCLE)
            waveView.centerTitleColor = mActivity.getColorRes(R.color.colorTextWhite)
            waveView.bottomTitleColor = mActivity.getColorRes(R.color.colorTextWhite)
            waveView.borderWidth = 10f
            waveView.setAmplitudeRatio(30)
            waveView.waveColor = mActivity.getColorRes(R.color.colorBackgroundSeaBlue)
            waveView.borderColor = mActivity.getColorRes(R.color.colorBackgroundBlack)
            waveView.setTopTitleStrokeColor(Color.BLUE)
            waveView.setTopTitleStrokeWidth(3f)
            waveView.setAnimDuration(3000)
            //        mWaveLoadingView.pauseAnimation();
            //        mWaveLoadingView.resumeAnimation();
            //        mWaveLoadingView.cancelAnimation();
            waveView.startAnimation()


        } catch (e: Exception) {

        }
    }

    override fun onResume() {
        super.onResume()
        activity!!.registerReceiver(this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    override fun onStop() {
        super.onStop()
        try {
            activity!!.unregisterReceiver(mBatInfoReceiver)
        } catch (e: Exception) {

        }
    }

    private fun setupTextViewTime(time: Time) {
        hourn.text = time.hourn.toString() + ""
        minutes.text = time.minutes.toString() + ""

        hourp.text = time.hourp.toString() + ""
        minutesp.text = time.minutep.toString() + ""

        houru.text = time.houru.toString() + ""
        minutesu.text = time.minutesu.toString() + ""

        if (sharedpreferences?.getString("mode", "0") == "0") {
            hourmain.text = time.hourn.toString() + ""
            minutesmain.text = time.minutes.toString() + ""
        }

        if (sharedpreferences?.getString("mode", "0") == "1") {
            hourmain.text = time.hourp.toString() + ""
            minutesmain.text = time.minutep.toString() + ""
        }
    }

    private fun setupCheckBatteryLevel(level: Int) {
        if (level <= 5) {
            val time = Time(0, 15, 2, 25, 3, 55)
            setupTextViewTime(time)
        } else if (level in 6..10) {
            val time = Time(0, 30, 3, 5, 6, 0)
            setupTextViewTime(time)
        } else if (level in 11..15) {
            val time = Time(0, 45, 3, 50, 8, 25)
            setupTextViewTime(time)
        } else if (level in 16..25) {
            val time = Time(1, 30, 4, 45, 12, 55)
            setupTextViewTime(time)
        } else if (level in 26..35) {
            val time = Time(2, 20, 6, 2, 19, 2)
            setupTextViewTime(time)
        } else if (level in 36..50) {
            val time = Time(5, 20, 9, 25, 22, 0)
            setupTextViewTime(time)
        } else if (level in 51..65) {
            val time = Time(7, 30, 11, 1, 28, 15)
            setupTextViewTime(time)
        } else if (level in 66..75) {
            val time = Time(9, 10, 14, 25, 30, 55)
            setupTextViewTime(time)
        } else if (level in 76..85) {
            val time = Time(14, 15, 17, 10, 38, 5)
            setupTextViewTime(time)
        } else if (level in 86..100) {
            val time = Time(20, 45, 30, 0, 60, 55)
            setupTextViewTime(time)
        }
    }

}
