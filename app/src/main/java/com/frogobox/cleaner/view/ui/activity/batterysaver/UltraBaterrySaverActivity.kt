package com.frogobox.cleaner.view.ui.activity.batterysaver

import android.os.Bundle
import android.os.Handler
import android.view.animation.OvershootInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.model.PowerItem
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.ui.activity.ApplyingUltraActivity
import com.frogobox.cleaner.view.adapter.PowerViewAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_ultra_popup.*

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * Cleaner
 * Copyright (C) 15/10/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.cleaner.model
 *
 */
class UltraBaterrySaverActivity : BaseActivity() {

    private var mAdapter: PowerViewAdapter? = null
    private val items: MutableList<PowerItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_ultra_popup)
        setupHourTime()
        setupButtonUI()
        setupRecyclerView()
        setupAdapterHandler()

    }

    private fun setupButtonUI() {
        applied.setOnClickListener {
            baseStartActivity<ApplyingUltraActivity>()
            finish()
        }
    }

    private fun setupRecyclerView() {
        mAdapter = PowerViewAdapter(items)
        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.itemAnimator!!.addDuration = 200
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }

    private fun setupHourTime() {
        val extraBundles = intent.extras
        var hour: Int
        var min: Int

        try {
            hour = Integer.parseInt(extraBundles?.getString(Constant.Variable.EXTRA_HOURS)!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_HOURS_NORMAL)!!.replace("[^0-9]".toRegex(), ""))
            min = Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES)!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES_NORMAL)!!.replace("[^0-9]".toRegex(), ""))
        } catch (e: Exception) {
            hour = 4
            min = 7
        }

        if (hour == 0 && min == 0) {
            hour = 4
            min = 7
        }

        addedtime.text = "(+" + hour + " h " + Math.abs(min) + " m)"
        addedtimedetail.text = "Extended Battery Up to " + Math.abs(hour) + "h " + Math.abs(min) + "m"
    }

    private fun setupAdapterHandler() {
        Handler().postDelayed({ add("Limit Brightness Upto 90%", 0) }, 1000)
        Handler().postDelayed({ add("Decrease Device Performance", 1) }, 2000)
        Handler().postDelayed({ add("Close All Battery Consuming Apps", 2) }, 3000)
        Handler().postDelayed({ add("Use Black and White Scheme To Avoid Battery Draning", 3) }, 4000)
        Handler().postDelayed({ add("Block Acess to Memory and Battery Draning Apps", 4) }, 5000)
        Handler().postDelayed({ add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5) }, 6000)
    }

    private fun add(text: String, position: Int) {
        val item = PowerItem(text)
        items.add(item)
        mAdapter!!.notifyItemInserted(position)
    }

}