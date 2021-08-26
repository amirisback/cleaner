package com.frogobox.cleaner.mvvm.junk

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import com.frogobox.cleaner.R
import com.frogobox.cleaner.core.BaseFragment
import com.frogobox.cleaner.databinding.FragmentJunkCleanerBinding
import com.frogobox.cleaner.service.JunkBroadcastReceiver
import com.frogobox.cleaner.utils.Constant.SHARED_PREF_JUNK
import com.frogobox.cleaner.utils.Constant.SHARED_PREF_WASEEM
import com.frogobox.cleaner.utils.Constant.ZERO_MB
import com.frogobox.cleaner.utils.Constant._MB
import java.util.Random

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

class JunkCleanerFragment : BaseFragment<FragmentJunkCleanerBinding>() {

    private var alljunk: Int = 0

    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun setupViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentJunkCleanerBinding {
        return FragmentJunkCleanerBinding.inflate(inflater, container, false)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        sharedpreferences = mActivity.getSharedPreferences(SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
        editor = sharedpreferences.edit()

        if (sharedpreferences.getString(SHARED_PREF_JUNK, "1") == "1") {
            setupDirtyFromJunk()
        } else {
            setupCleanFromJunk()
        }
    }

    private fun setupDoInClenerJunk() {
        setupShowAdsInterstitial()
        baseStartActivity<JunkCleanerActivity, String>(SHARED_PREF_JUNK, alljunk.toString() + "")
        Handler().postDelayed({
            //Do something after 100ms
            setupCleanFromJunk()
            editor.putString(SHARED_PREF_JUNK, "0")
            editor.apply()
            setupAlarmManager()
        }, 2000)
    }

    private fun setupAlarmManager() {
        val intent = Intent(mActivity, JunkBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(mActivity, 0,
                intent, PendingIntent.FLAG_ONE_SHOT)
        val alarmManager = mActivity.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 600 * 1000, pendingIntent)
    }

    private fun setupDirtyFromJunk() {
        val proc1 = Random().nextInt(50) + 5
        val proc2 = Random().nextInt(15) + 10
        val proc3 = Random().nextInt(30) + 15
        val proc4 = Random().nextInt(25) + 10
        alljunk = proc1 + proc2 + proc3 + proc4

        val mainString = alljunk.toString() + _MB
        val cacheString = proc1.toString() + _MB
        val tempString = proc2.toString() + _MB
        val residueString = proc3.toString() + _MB
        val systemString = proc4.toString() + _MB

        binding.apply {

            setOptimizeButton(mainbutton, R.string.button_clean)
            ivIconJunk.setImageResource(R.drawable.ic_junk_dirty)
            cache.setBackgroundResource(R.drawable.bg_circle_border_red)
            temp.setBackgroundResource(R.drawable.bg_circle_border_red)
            residue.setBackgroundResource(R.drawable.bg_circle_border_red)
            system.setBackgroundResource(R.drawable.bg_circle_border_red)

            setupTextValueColor(tvStateCondition1, mainString, colorTextRed())
            setupTextValueColor(tvStateCondition2, getString(R.string.text_junk_condition_2_dirty), colorTextRed())
            setupTextValueColor(cachetext, cacheString, colorTextRed())
            setupTextValueColor(temptext, tempString, colorTextRed())
            setupTextValueColor(residuetext, residueString, colorTextRed())
            setupTextValueColor(systemtext, systemString, colorTextRed())

            mainbutton.setOnClickListener {
                setupDoInClenerJunk()
            }
        }

    }

    private fun setupCleanFromJunk() {
        binding.apply {

            setDoneOptimizeButton(mainbutton, R.string.button_cleaned)
            ivIconJunk.setImageResource(R.drawable.ic_junk_clean)
            cache.setBackgroundResource(R.drawable.bg_circle_border_green)
            temp.setBackgroundResource(R.drawable.bg_circle_border_green)
            residue.setBackgroundResource(R.drawable.bg_circle_border_green)
            system.setBackgroundResource(R.drawable.bg_circle_border_green)

            setupTextValueColor(tvStateCondition1, getString(R.string.text_junk_condition_1_clean), colorTextGreen())
            setupTextValueColor(tvStateCondition2, getString(R.string.text_junk_condition_2_clean), colorTextGreen())
            setupTextValueColor(cachetext, ZERO_MB, colorTextGreen())
            setupTextValueColor(temptext, ZERO_MB, colorTextGreen())
            setupTextValueColor(residuetext, ZERO_MB, colorTextGreen())
            setupTextValueColor(systemtext, ZERO_MB, colorTextGreen())

            mainbutton.setOnClickListener {
                showCustomToast(getString(R.string.toast_cleaned_junk))
            }

        }
    }

}