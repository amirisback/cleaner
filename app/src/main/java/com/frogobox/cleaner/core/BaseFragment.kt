package com.frogobox.cleaner.core

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.frogobox.admob.core.FrogoAdmob
import com.frogobox.cleaner.R
import com.frogobox.sdk.core.FrogoFragment
import com.google.android.gms.ads.AdView
import com.google.gson.Gson

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
abstract class BaseFragment<VB : ViewBinding> : FrogoFragment<VB>() {

    protected lateinit var mActivity: BaseActivity<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity as BaseActivity<*>)
    }

    protected fun setupShowAdsBanner(mAdView: AdView) {
        FrogoAdmob.Banner.setupBanner(mAdView)
        FrogoAdmob.Banner.showBanner(mAdView)
    }

    protected fun setupShowAdsInterstitial() {
        FrogoAdmob.Interstitial.showInterstitial(mActivity)
    }

    protected fun showCustomToast(message: String) {
        val layout = LayoutInflater.from(context).inflate(R.layout.toast_apps, null)
        val text = layout.findViewById<TextView>(R.id.toast_tv)
        text.text = message
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    protected fun setOptimizeButton(button: Button, text: Int) {
        button.setBackgroundResource(R.drawable.bg_border_button_positive)
        button.setTextColor(mActivity.getColorRes(R.color.colorTextWhite))
        button.text = getString(text)
    }

    protected fun setDoneOptimizeButton(button: Button, text: Int) {
        button.setBackgroundResource(R.drawable.bg_border_button_negatif)
        button.setTextColor(mActivity.getColorRes(R.color.colorPrimaryText))
        button.text = getString(text)
    }

    protected fun setupTextValueColor(textView: TextView, text: String, color: Int) {
        textView.text = text
        textView.setTextColor(color)
    }

    protected fun colorTextGreen(): Int {
        return mActivity.getColorRes(R.color.colorTextGreen)
    }

    protected fun colorTextRed(): Int {
        return mActivity.getColorRes(R.color.colorTextRed)
    }

    protected fun colorPrimary(): Int {
        return mActivity.getColorRes(R.color.colorPrimary)
    }

    protected fun colorAccent(): Int {
        return mActivity.getColorRes(R.color.colorAccent)
    }

}