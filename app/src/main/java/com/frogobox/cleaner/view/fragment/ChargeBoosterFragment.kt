package com.frogobox.cleaner.view.fragment

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
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import android.widget.ImageView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseFragment
import com.frogobox.cleaner.service.AlarmBoosterBroadcastReceiver
import com.frogobox.cleaner.utils.Constant
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.fragment_charge_booster.*
import java.io.RandomAccessFile
import java.text.DecimalFormat
import java.util.*
import java.util.regex.Pattern

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

class ChargeBoosterFragment : BaseFragment() {

//    companion object {
//        lateinit var optimizeButton : ImageView
//    }

    private val mb = 1024 * 1024
    private var timer: TimerTask? = null
    private var timer2: TimerTask? = null
    private var x: Int = 0
    private var y: Int = 0
    private var counter = 0
    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    // Get the Number value from the string
    // System.out.println("Ram : " + value);
    // totRam = totRam / 1024;
    // Streams.close(reader);
    val totalRAM: String
        get() {

            var reader: RandomAccessFile? = null
            var load: String? = null
            val twoDecimalForm = DecimalFormat("#.##")
            var totRam = 0.0
            var lastValue = ""
            try {
                try {
                    reader = RandomAccessFile("/proc/meminfo", "r")
                    load = reader.readLine()
                } catch (e: Exception) {

                }

                val p = Pattern.compile("(\\d+)")
                val m = p.matcher(load)
                var value = ""
                while (m.find()) {
                    value = m.group(1)
                }

                try {
                    reader!!.close()
                } catch (e: Exception) {
                }

                totRam = java.lang.Double.parseDouble(value)

                val mb = totRam / 1024.0
                val gb = totRam / 1048576.0
                val tb = totRam / 1073741824.0

                if (tb > 1) {
                    lastValue = twoDecimalForm.format(tb) + " TB"
                } else if (gb > 1) {
                    lastValue = twoDecimalForm.format(gb) + " GB"
                } else if (mb > 1) {
                    lastValue = twoDecimalForm.format(mb) + " MB"
                } else {
                    lastValue = twoDecimalForm.format(totRam) + " KB"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }

            return lastValue
        }

    private val usedMemorySize: Long
        get() {
            try {
                val mi = ActivityManager.MemoryInfo()
                val activityManager = activity!!.getSystemService(ACTIVITY_SERVICE) as ActivityManager
                activityManager.getMemoryInfo(mi)
                return mi.availMem / 1048576L
            } catch (e: Exception) {
                return 200
            }
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        optimizeButton = optbutton
        return inflater.inflate(R.layout.fragment_charge_booster, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedpreferences = activity!!.getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE)

        try {
            val ran3 = Random()
            ramperct!!.text = (ran3.nextInt(60) + 40).toString() + "%"

            optbutton.setBackgroundResource(0)
            optbutton.setImageResource(0)
            optbutton.setImageResource(R.drawable.bg_button_optimize)

            if (sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_BOOSTER, "1") == "0") {
                optbutton.setImageResource(0)
                optbutton.setImageResource(R.drawable.bg_button_optimized)
                centree!!.text = sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_VALUE, "50MB")
            }

            start()

            optbutton.setOnClickListener {
                if (sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_BOOSTER, "1") == "1") {
                    optimize()

                    editor = sharedpreferences!!.edit()
                    editor!!.putString(Constant.Variable.SHARED_PREF_BOOSTER, "0")
                    editor!!.commit()

                    val intent = Intent(activity, AlarmBoosterBroadcastReceiver::class.java)

                    val pendingIntent = PendingIntent.getBroadcast(activity, 0,
                            intent, PendingIntent.FLAG_ONE_SHOT)

                    val alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager
                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 100 * 1000, pendingIntent)
                } else {
                    showCustomToast(getString(R.string.toast_phone_already_optimized))
                }
            }

            //#F22938 red
            // Create background track

            //    background_button_optimize();
        } catch (e: Exception) {

        }
    }

    fun optimize() {

        val rotate = RotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 5000
        rotate.interpolator = LinearInterpolator()

        val image = view!!.findViewById<ImageView>(R.id.circularlines)

        image.startAnimation(rotate)


        dynamicArcView2!!.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, 100f, 0f)
                .setInterpolator(AccelerateInterpolator())
                .build())

        dynamicArcView2!!.addSeries(SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        // Create data series track
        val seriesItem1 = SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .build()

        val seriesItem2 = SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundSeaBlue))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .build()
        //
        //        int series1Index = dynamicArcView2.addSeries(seriesItem1);
        val series1Index2 = dynamicArcView2!!.addSeries(seriesItem2)

        dynamicArcView2!!.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(2000)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {
                        bottom!!.text = ""
                        top!!.text = ""
                        centree!!.text = "Optimizing..."
                    }

                    override fun onEventEnd(decoEvent: DecoEvent) {

                    }
                })
                .build())

        dynamicArcView2!!.addEvent(DecoEvent.Builder(25f).setIndex(series1Index2).setDelay(4000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {
                bottom!!.text = ""
                top!!.text = ""
                centree!!.text = "Optimizing..."
            }

            override fun onEventEnd(decoEvent: DecoEvent) {
                mActivity.setupShowAdsInterstitial()
                bottom!!.text = "Found"
                top!!.text = "Storage"
                val ran3 = Random()
                ramperct!!.text = (ran3.nextInt(40) + 20).toString() + "%"
            }
        }).build())

        val img_animation = view!!.findViewById<ImageView>(R.id.waves)

        val animation = TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f)          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.duration = 5000  // animation duration
        animation.repeatCount = 0
        animation.interpolator = LinearInterpolator()// animation repeat count
        //        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        animation.fillAfter = true

        img_animation.startAnimation(animation)

        val counter = 0
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

                scanlay!!.visibility = View.VISIBLE
                optimizelay!!.visibility = View.GONE
                scanning!!.text = "SCANNING..."
            }

            override fun onAnimationEnd(animation: Animation) {
                scanlay!!.visibility = View.GONE
                optimizelay!!.visibility = View.VISIBLE
                //                optbutton.setOnClickListener(null);
                optbutton.setImageResource(R.drawable.bg_button_optimized)


                val ran = Random()
                x = ran.nextInt(100) + 30

                val ran2 = Random()
                val proc = ran2.nextInt(10) + 5

                centree!!.text = (usedMemorySize - x).toString() + " MB"

                editor = sharedpreferences!!.edit()
                editor!!.putString(Constant.Variable.SHARED_PREF_VALUE, (usedMemorySize - x).toString() + " MB")
                editor!!.commit()

                Log.e("used mem", "$usedMemorySize MB")
                Log.e("used mem", totalRAM)

                totalram!!.text = totalRAM
                usedram!!.text = (usedMemorySize - x).toString() + " MB/ "

                appsfreed!!.text = totalRAM
                appsused!!.text = Math.abs(usedMemorySize - x.toLong() - 30).toString() + " MB/ "

                processes!!.text = (y - proc).toString() + ""

            }

            override fun onAnimationRepeat(animation: Animation) {

            }
        })
    }


    fun start() {


        val t = Timer()
        timer = object : TimerTask() {

            override fun run() {

                try {
                    activity!!.runOnUiThread {
                        //                        optbutton.setBackgroundResource(0);
                        //                        optbutton.setBackgroundResource(R.drawable.background_button_optimize);
                        counter++
                        centree!!.text = counter.toString() + "MB"
                    }
                } catch (e: Exception) {

                }


            }

        }
        t.schedule(timer, 30, 30)


        val ran2 = Random()
        val proc = ran2.nextInt(60) + 30


        dynamicArcView2!!.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, 100f, 0f)
                .setInterpolator(AccelerateInterpolator())
                .build())

        dynamicArcView2!!.addSeries(SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(32f)
                .build())

        //Create data series track
        val seriesItem1 = SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .build()

        val seriesItem2 = SeriesItem.Builder(mActivity.getColorRes(R.color.colorBackgroundSeaBlue))
                .setRange(0f, 100f, 0f)
                .setLineWidth(32f)
                .build()


        //
        //        int series1Index = dynamicArcView2.addSeries(seriesItem1);
        val series1Index2 = dynamicArcView2!!.addSeries(seriesItem2)

        dynamicArcView2!!.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(600)
                .build())


        dynamicArcView2!!.addEvent(DecoEvent.Builder(proc.toFloat()).setIndex(series1Index2).setDelay(2000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {


            }

            override fun onEventEnd(decoEvent: DecoEvent) {

                t.cancel()
                timer!!.cancel()
                t.purge()


                centree!!.text = "$usedMemorySize MB"

                if (sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_BOOSTER, "1") == "0") {

                    centree!!.text = sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_VALUE, "50MB")
                }


                val t = Timer()
                val t2 = Timer()


                try {

                    timer2 = object : TimerTask() {

                        override fun run() {

                            try {
                                activity!!.runOnUiThread {
                                    centree!!.text = "$usedMemorySize MB"

                                    if (sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_BOOSTER, "1") == "0") {

                                        centree!!.text = sharedpreferences!!.getString(Constant.Variable.SHARED_PREF_VALUE, "50MB")
                                    }

                                    t2.cancel()
                                    timer2!!.cancel()
                                    t2.purge()
                                }
                            } catch (e: Exception) {

                            }

                        }

                    }
                    //Set the schedule function and rate
                } catch (e: Exception) {

                }

                t2.schedule(timer2, 100, 100)


            }
        }).build())
        //        centree.setText(getUsedMemorySize()+" MB");
        Log.e("used mem", "$usedMemorySize MB")
        Log.e("used mem", totalRAM)

        totalram!!.text = totalRAM
        usedram!!.text = "$usedMemorySize MB/ "
        appsfreed!!.text = totalRAM
        appsused!!.text = (usedMemorySize - x.toLong() - 30).toString() + " MB/ "

        val ran = Random()
        y = ran.nextInt(50) + 15

        processes!!.text = y.toString() + ""


    }

}
