package com.frogobox.cleaner.view.fragment

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseFragment
import com.frogobox.cleaner.service.AlarmJunkBroadcastReceiver
import com.frogobox.cleaner.utils.Constant.Variable.*
import com.frogobox.cleaner.view.activity.ScanningJunkActivity
import kotlinx.android.synthetic.main.fragment_junk_cleaner.*
import java.util.*

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

class JunkCleanerFragment : BaseFragment() {

    private val checkvar = 0
    private var alljunk: Int = 0

    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_junk_cleaner, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCheckJunk()
    }

    private fun setupCheckJunk() {
        try {
            sharedpreferences = activity!!.getSharedPreferences(SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
            if (sharedpreferences!!.getString(SHARED_PREF_JUNK, "1") == "1") {
                setupDirtyFromJunk()
            } else {
                setupCleanFromJunk()
            }
            mainbutton!!.setOnClickListener {
                if (sharedpreferences!!.getString(SHARED_PREF_JUNK, "1") == "1") {
                    setupDoInClenerJunk()
                } else {
                    showCustomToast(getString(R.string.toast_cleaned_junk))
                }
            }
        } catch (e: Exception) {
        }

    }

    private fun setupText(textView: TextView, text: String, color: Int) {
        textView.text = text
        textView.setTextColor(color)
    }

    private fun setupDoInClenerJunk() {

        baseStartActivity<ScanningJunkActivity, String>(SHARED_PREF_JUNK, alljunk.toString() + "")

        val handler = Handler()
        handler.postDelayed({
            //Do something after 100ms
            setupCleanFromJunk()
            editor = sharedpreferences?.edit()
            editor?.putString(SHARED_PREF_JUNK, "0")
            editor?.commit()
            val intent = Intent(activity, AlarmJunkBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(activity, 0,
                    intent, PendingIntent.FLAG_ONE_SHOT)
            val alarmManager = activity!!.getSystemService(ALARM_SERVICE) as AlarmManager
            alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 600 * 1000, pendingIntent)
        }, 2000)
    }

    private fun setupDirtyFromJunk() {
        mainbrush.setImageResource(R.drawable.img_junktop_red)
        mainbutton.setImageResource(R.drawable.bg_button_clean)
        cache.setImageResource(R.drawable.img_junk_red_cache)
        temp.setImageResource(R.drawable.img_junk_red_temp)
        residue.setImageResource(R.drawable.img_junk_red_residual)
        system.setImageResource(R.drawable.img_junk_red_system)

        val proc1 = Random().nextInt(20) + 5
        val proc2 = Random().nextInt(15) + 10
        val proc3 = Random().nextInt(30) + 15
        val proc4 = Random().nextInt(25) + 10
        alljunk = proc1 + proc2 + proc3 + proc4

        val mainString = alljunk.toString() + _MB
        val cacheString = proc1.toString() + _MB
        val tempString = proc2.toString() + _MB
        val residueString = proc3.toString() + _MB
        val systemString = proc4.toString() + _MB

        val colorText = Color.parseColor(COLOR_RED)

        setupText(maintext, mainString, colorText)
        setupText(cachetext, cacheString, colorText)
        setupText(temptext, tempString, colorText)
        setupText(residuetext, residueString, colorText)
        setupText(systemtext, systemString, colorText)

    }

    private fun setupCleanFromJunk() {
        mainbrush.setImageResource(R.drawable.img_junktop_blue)
        mainbutton.setImageResource(R.drawable.bg_button_cleaned)
        cache.setImageResource(R.drawable.img_junk_green_cache)
        temp.setImageResource(R.drawable.img_junk_green_temp)
        residue.setImageResource(R.drawable.img_junk_green_residual)
        system.setImageResource(R.drawable.img_junk_green_system)

        val color = Color.parseColor(COLOR_GREEN)

        setupText(maintext, "CRYSTAL CLEAR", color)
        setupText(cachetext, ZERO_MB, color)
        setupText(temptext, ZERO_MB, color)
        setupText(residuetext, ZERO_MB, color)
        setupText(systemtext, ZERO_MB, color)
    }

}