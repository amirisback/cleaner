package com.frogobox.cleaner.view.activity.batterysaver

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.model.PowerItem
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.activity.PowerSavingComplitionActivity
import com.frogobox.cleaner.view.adapter.PowerViewAdapter

import java.util.ArrayList

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_powersaving_popup.*

/**
 * Created by Frogobox Software Industries 2/19/2017.
 */

class PowerSavingBatterySaverActivity : BaseActivity() {

    private var mAdapter: PowerViewAdapter? = null
    private val items: MutableList<PowerItem> = mutableListOf()
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_powersaving_popup)
        setupHourTime()
        setupButtonUI()
        setupRecyclerView()
        setupAdapterHandler()

    }

    private fun setupHourTime(){
        val extraBundles = intent.extras

        val sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE)
        editor = sharedpreferences.edit()

        var hour: Int
        var min: Int
        try {
            hour = Integer.parseInt(extraBundles!!.getString(Constant.Variable.EXTRA_HOURS)!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_HOURS_NORMAL)!!.replace("[^0-9]".toRegex(), ""))
            min = Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES)!!.replace("[^0-9]".toRegex(), "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES_NORMAL)!!.replace("[^0-9]".toRegex(), ""))
        } catch (e: Exception) {
            hour = 3
            min = 5
        }

        if (hour == 0 && min == 0) {
            hour = 3
            min = 5
        }

        addedtime.text = "(+" + hour + " h " + Math.abs(min) + " m)"
        addedtimedetail.text = "Extended Battery Up to " + Math.abs(hour) + "h " + Math.abs(min) + "m"

    }

    private fun setupButtonUI(){
        applied.setOnClickListener {
            editor!!.putString("mode", "1")
            editor!!.commit()
            baseStartActivity<PowerSavingComplitionActivity>()
            finish()
        }
    }

    private fun setupRecyclerView() {
        mAdapter = PowerViewAdapter(items)
        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.itemAnimator!!.addDuration = 200
        recycler_view.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }

    private fun setupAdapterHandler() {
        Handler().postDelayed({ add("Limit Brightness Upto 80%", 0) }, 1000)
        Handler().postDelayed({ add("Decrease Device Performance", 1) }, 2000)
        Handler().postDelayed({ add("Close All Battery Consuming Apps", 2) }, 3000)
        Handler().postDelayed({ add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3) }, 4000)
    }

    private fun add(text: String, position: Int) {
        val item = PowerItem(text)
        items.add(item)
        mAdapter!!.notifyItemInserted(position)
    }

}