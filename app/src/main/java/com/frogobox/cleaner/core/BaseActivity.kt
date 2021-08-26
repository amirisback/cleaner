package com.frogobox.cleaner.core

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.frogobox.admob.core.FrogoAdmob
import com.frogobox.admob.core.IFrogoAdmob
import com.frogobox.cleaner.R
import com.frogobox.sdk.core.FrogoActivity
import com.google.android.gms.ads.AdView


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
abstract class BaseActivity<VB: ViewBinding> : FrogoActivity<VB>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupAdmob()
    }

    private fun setupAdmob() {
        setupAdsPublisher(getString(R.string.admob_publisher_id))
        setupAdsBanner(getString(R.string.admob_banner))
        setupAdsInterstitial(getString(R.string.admob_interstitial))
    }

    private fun setupAdsPublisher(mPublisherId: String) {
        FrogoAdmob.setupPublisherID(mPublisherId)
        FrogoAdmob.Publisher.setupPublisher(this)
    }

    private fun setupAdsBanner(mAdUnitId: String) {
        FrogoAdmob.setupBannerAdUnitID(mAdUnitId)
    }

    private fun setupAdsInterstitial(mAdUnitId: String) {
        FrogoAdmob.setupInterstialAdUnitID(mAdUnitId)
        FrogoAdmob.Interstitial.setupInterstitial(this)
    }

    protected fun setupShowAdsBanner(mAdView: AdView) {
        FrogoAdmob.Banner.setupBanner(mAdView)
        FrogoAdmob.Banner.showBanner(mAdView)
    }

    protected fun setupShowAdsInterstitial() {
        FrogoAdmob.Interstitial.showInterstitial(this)
    }

    protected fun setupNoLimitStatBar() {
        val windows = window // in Activity's onCreate() for instance
        windows.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    protected fun showCustomToast(message: String) {
        val layout = LayoutInflater.from(this).inflate(R.layout.toast_apps, null)
        val text = layout.findViewById<TextView>(R.id.toast_tv)
        text.text = message
        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    override fun setupDetailActivity(title: String) {
        setTitle(title)
        val upArrow = ContextCompat.getDrawable(this, R.drawable.ic_toolbar_back_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setBackgroundDrawable(
                ColorDrawable(
                        ContextCompat.getColor(
                                this,
                                R.color.colorBaseWhite
                        )
                )
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getColorRes(res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

}