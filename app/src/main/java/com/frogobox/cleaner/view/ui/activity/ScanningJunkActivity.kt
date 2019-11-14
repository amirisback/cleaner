package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.model.Apps
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.adapter.JunkAppsViewAdapter
import com.frogobox.cleaner.view.adapter.SimpleDividerItemDecoration
import com.github.ybq.android.spinkit.style.ThreeBounce
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_scanning_junk.*
import java.util.*

/**
 * Created by Frogobox Software Industries 2/24/2017.
 */

class ScanningJunkActivity : BaseActivity() {

    private var check = 0
    private var prog = 0

    private lateinit var packages: List<ApplicationInfo>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning_junk)

        packages = packageManager.getInstalledApplications(0)
        setupAnimationProcess()
        setupRecyclerViewApps()
    }

    private fun setupAnimationProcess(){
        val timer = Timer()
        val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = 4
        rotate.interpolator = LinearInterpolator()

        rotate.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                timer.cancel()
                timer.purge()
                scan1.hide()
                scan2.hide()
                scan3.hide()
                scan4.hide()
                scan5.hide()
                scan6.hide()

                files.text = ""
            }

            override fun onAnimationRepeat(animation: Animation) {
                check++
                startAnim(check)
            }
        })
        front.startAnimation(rotate)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (prog < packages.size) {
                        files.text = "" + packages[prog].sourceDir
                        prog++
                    } else {
                        timer.cancel()
                        timer.purge()
                    }
                }
            }
        }, 80, 80)
    }

    private fun setupRecyclerViewApps(){
        val apps = mutableListOf<Apps>()
        val junkAppsViewAdapter = JunkAppsViewAdapter(apps)

        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.addItemDecoration(SimpleDividerItemDecoration(this))
        recycler_view.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = junkAppsViewAdapter
        junkAppsViewAdapter.notifyDataSetChanged()
        recycler_view.addItemDecoration(SimpleDividerItemDecoration(this))

        setupContentRecyclerView(apps, junkAppsViewAdapter)
    }

    private fun setupContentRecyclerView(apps: MutableList<Apps>, adapter: JunkAppsViewAdapter) {
        Handler().postDelayed({ addArrayApps(apps, adapter, 0) }, 1000)
        Handler().postDelayed({ addArrayApps(apps, adapter, 1) }, 2000)
        Handler().postDelayed({ addArrayApps(apps, adapter, 2) }, 3000)
        Handler().postDelayed({ addArrayApps(apps, adapter, 3) }, 4000)
        Handler().postDelayed({ removeArrayApps(apps, adapter, 0) }, 5000)
        Handler().postDelayed({ removeArrayApps(apps, adapter, 0) }, 6000)
        Handler().postDelayed({ removeArrayApps(apps, adapter, 0) }, 7000)

        Handler().postDelayed({
            removeArrayApps(apps, adapter, 0)

            rippleBackground.startRippleAnimation()
            front.setImageResource(R.drawable.ic_task_done_background)
            back.setImageResource(R.drawable.ic_task_done_main)

            spin_kit.setIndeterminateDrawable(ThreeBounce())
            spin_kit.visibility = View.GONE

            scanning.setPadding(20, 0, 0, 0)

            if (Build.VERSION.SDK_INT < 23) {
                scanning.setTextAppearance(applicationContext, android.R.style.TextAppearance_Medium)
                scanning.text = intent.extras?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared"
            } else {
                scanning.setTextAppearance(android.R.style.TextAppearance_Medium)
                scanning.text = intent.extras?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared"
            }

            val anim = AnimatorInflater.loadAnimator(this, R.animator.flipping) as ObjectAnimator
            anim.target = front
            anim.duration = 3000
            anim.start()

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    scanning.text = "Cleared " + intent.extras?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB"
                }
                override fun onAnimationEnd(animation: Animator) { setupFinishCleaningJunk() }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

            files.text = ""
        }, 8000)
    }

    private fun setupFinishCleaningJunk(){
        setupShowAdsInterstitial()
        rippleBackground.stopRippleAnimation()
        Handler().postDelayed({ finish() }, 1000)
    }

    private fun startAnim(timePosition: Int) {
        when (timePosition) {
            1 -> {
                scan1.show()
                scan3.show()
                scan5.show()

                scan2.hide()
                scan4.hide()
                scan6.hide()
            }
            2 -> {
                scan2.show()
                scan4.show()
                scan6.show()

                scan1.hide()
                scan3.hide()
                scan5.hide()
            }
            3 -> {
                scan2.show()
                scan4.show()
                scan6.show()

                scan1.show()
                scan3.show()
                scan5.show()
            }
            4 -> {
                scan2.show()
                scan3.show()
                scan5.show()

                scan1.show()
                scan2.show()
                scan6.show()
            }
        }
    }

    private fun addArrayApps(apps: MutableList<Apps>, adapter: JunkAppsViewAdapter, position: Int) {

        val positionRandom = 0 + (Math.random() * (packages.size - 1 - 0 + 1)).toInt()
        val packageName = packages[positionRandom].packageName
        val pName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)) as String
        val a = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        val ico = packageManager.getApplicationIcon(packages[positionRandom].packageName)

        apps.add(Apps(packages[positionRandom].dataDir, ico))
        adapter.notifyItemInserted(position)
    }

    private fun removeArrayApps(apps: MutableList<Apps>, adapter: JunkAppsViewAdapter, position: Int) {
        adapter.notifyItemRemoved(position)
        apps.removeAt(position)
    }

}