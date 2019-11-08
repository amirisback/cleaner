package com.frogobox.cleaner.view.ui.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.utils.PagerAdapter
import com.frogobox.cleaner.view.ui.fragment.BatterySaverFragment
import com.frogobox.cleaner.view.ui.fragment.CPUCoolerFragment
import com.frogobox.cleaner.view.ui.fragment.ChargeBoosterFragment
import com.frogobox.cleaner.view.ui.fragment.JunkCleanerFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar_main.*

class MainActivity : BaseActivity() {

    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)

        setupInitGlobalVariable()
        setupInitThreadHandler()
        setupDeclareTabLayoutViewPager()
        setupViewPagerChangeListener()
        setupTabSelectedListener()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.toolbar_menu_about -> {
                baseStartActivity<AboutUsActivity>()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupInitGlobalVariable() {
        sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE)
        editor = sharedpreferences?.edit()
    }

    private fun setupInitThreadHandler() {
        val oldHandler = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { paramThread, paramThrowable ->
            //Do your own error handling here
            if (oldHandler != null)
                oldHandler.uncaughtException(
                        paramThread,
                        paramThrowable
                ) //Delegates to Android's error handling
            else
                System.exit(2) //Prevents the service/app from freezing
        }
    }

    private fun setupDeclareTabLayoutViewPager() {
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_tablayout_chargebooster))
//        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_tablayout_batterysaver))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_tablayout_cooler))
        tab_layout.addTab(tab_layout.newTab().setIcon(R.drawable.ic_tablayout_junkcleaner))
        tab_layout.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = PagerAdapter(supportFragmentManager)

        adapter.addFragment(ChargeBoosterFragment(), getString(R.string.title_charge_booster))
//        adapter.addFragment(BatterySaverFragment(), getString(R.string.title_baterry_saver))
        adapter.addFragment(CPUCoolerFragment(), getString(R.string.title_cpu_cooler))
        adapter.addFragment(JunkCleanerFragment(), getString(R.string.title_junk_cleaner))

        pager.adapter = adapter
        pager.offscreenPageLimit = 3

    }

    private fun setupViewPagerChangeListener() {
        pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tab_layout))
        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> supportActionBar?.setTitle(R.string.title_charge_booster)
//                    1 -> supportActionBar?.setTitle(R.string.title_baterry_saver)
                    1 -> supportActionBar?.setTitle(R.string.title_cpu_cooler)
                    2 -> supportActionBar?.setTitle(R.string.title_junk_cleaner)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
    }

    private fun setupTabSelectedListener() {
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                pager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        editor?.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "0")
        editor?.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "0")
        editor?.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "0")
        editor?.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "0")
        editor?.commit()
    }

}