package com.frogobox.cleaner.mvvm.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.core.BaseActivity
import com.frogobox.cleaner.databinding.ActivityMainBinding
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.utils.PagerAdapter
import com.frogobox.cleaner.mvvm.cpu.CpuCoolerFragment
import com.frogobox.cleaner.mvvm.junk.JunkCleanerFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun setupViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupInitGlobalVariable()
        setupInitThreadHandler()
        setupDeclareTabLayoutViewPager()
        setupViewPagerChangeListener()
        setupTabSelectedListener()
    }

    private fun setupInitGlobalVariable() {
        sharedpreferences = getSharedPreferences(Constant.SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE)
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

    private fun setupPagerAdapter(): PagerAdapter {
        return PagerAdapter(supportFragmentManager).apply {
            addFragment(CleanerFragment(), getString(R.string.title_charge_booster))
            addFragment(CpuCoolerFragment(), getString(R.string.title_cpu_cooler))
            addFragment(JunkCleanerFragment(), getString(R.string.title_junk_cleaner))
        }
    }

    private fun setupDeclareTabLayoutViewPager() {
        binding.apply {

            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bottom_nav_chargebooster))
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bottom_nav_temperature))
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_bottom_nav_junk))
            tabLayout.tabGravity = TabLayout.GRAVITY_FILL

            viewPager.adapter = setupPagerAdapter()
            viewPager.offscreenPageLimit = 3

        }
    }

    private fun setupViewPagerChangeListener() {

        binding.apply {

            viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
            viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}
                override fun onPageSelected(position: Int) {
                    when (position) {
                        0 -> supportActionBar?.setTitle(R.string.title_charge_booster)
                        1 -> supportActionBar?.setTitle(R.string.title_cpu_cooler)
                        2 -> supportActionBar?.setTitle(R.string.title_junk_cleaner)
                    }
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })


        }

    }

    private fun setupTabSelectedListener() {
        binding.apply {
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    viewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        editor?.putString(Constant.SHARED_PREF_BUTTON_1, "0")
        editor?.putString(Constant.SHARED_PREF_BUTTON_2, "0")
        editor?.putString(Constant.SHARED_PREF_BUTTON_3, "0")
        editor?.putString(Constant.SHARED_PREF_BUTTON_4, "0")
        editor?.commit()
    }



}