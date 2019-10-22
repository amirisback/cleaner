package com.frogobox.cleaner.base

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.frogobox.cleaner.R
import com.frogobox.cleaner.utils.AdmobHelper.Interstitial.setupInterstitial
import com.frogobox.cleaner.utils.AdmobHelper.Interstitial.showInterstitial
import com.frogobox.cleaner.utils.AdmobHelper.Publisher.setupPublisher
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.reward.RewardedVideoAd


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
abstract class BaseActivity : AppCompatActivity() {

    lateinit var mActivity: AppCompatActivity
    lateinit var mInterstitialAd: InterstitialAd
    lateinit var mRewardedVideoAd: RewardedVideoAd

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this
        setupAdmob()
    }

    private fun setupAdmob() {
        setupPublisher(this)
        setupAdmobInterstitial()
//        setupAdmobVideo(context)
    }

    private fun setupAdmobInterstitial() {
        mInterstitialAd = InterstitialAd(this)
        setupInterstitial(this, mInterstitialAd)
    }

    protected fun setupShowAdsInterstitial() {
        showInterstitial(mInterstitialAd)
    }

    protected fun setupNoLimitStatBar() {
        val windows = window // in Activity's onCreate() for instance
        windows.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }

    protected fun setupChildFragment(frameId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(frameId, fragment)
            commit()
        }
    }

    protected inline fun <reified ClassActivity> baseStartActivity() {
        this.startActivity(Intent(this, ClassActivity::class.java))
    }

    protected inline fun <reified ClassActivity, Model> baseStartActivity(
            extraKey: String,
            data: Model
    ) {
        val intent = Intent(this, ClassActivity::class.java)
        val extraData = BaseHelper().baseToJson(data)
        intent.putExtra(extraKey, extraData)
        this.startActivity(intent)
    }

    protected inline fun <reified Model> baseGetExtraData(extraKey: String): Model {
        val extraIntent = intent.getStringExtra(extraKey)
        val extraData = BaseHelper().baseFromJson<Model>(extraIntent)
        return extraData
    }

    protected fun checkExtra(extraKey: String): Boolean {
        return intent?.hasExtra(extraKey)!!
    }

    protected fun <Model> baseFragmentNewInstance(fragment: BaseFragment, argumentKey: String, extraDataResult: Model) {
        fragment.baseNewInstance(argumentKey, extraDataResult)
    }

    protected fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showCustomToast(message: String){
        val layout = LayoutInflater.from(this).inflate(R.layout.toast_apps, null)
        val text = layout.findViewById<TextView>(R.id.textView1)
        text.setText(message)
        val toast = Toast(this)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    protected fun setupDetailActivity(title: String) {
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

    protected fun setupEventEmptyView(view: View, isEmpty: Boolean) {
        if (isEmpty) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    protected fun setupEventProgressView(view: View, progress: Boolean) {
        if (progress) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    fun getColorRes(res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

}