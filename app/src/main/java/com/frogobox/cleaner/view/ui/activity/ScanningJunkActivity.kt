package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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
import kotlinx.android.synthetic.main.view_ads.*
import java.util.*

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

    private fun setupAnimationProcess() {
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
                iv_ball_indicator_1.hide()
                iv_ball_indicator_2.hide()
                iv_ball_indicator_3.hide()
                iv_ball_indicator_4.hide()
                iv_ball_indicator_5.hide()
                iv_ball_indicator_6.hide()

                tv_files_data.text = ""
            }

            override fun onAnimationRepeat(animation: Animation) {
                check++
                startAnim(check)
            }
        })
        iv_scanning_main.startAnimation(rotate)

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    if (prog < packages.size) {
                        tv_files_data.text = packages[prog].sourceDir
                        prog++
                    } else {
                        timer.cancel()
                        timer.purge()
                    }
                }
            }
        }, 80, 80)
    }

    private fun setupRecyclerViewApps() {
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
            iv_scanning_background.visibility = View.INVISIBLE
            iv_scanning_main.visibility = View.INVISIBLE
            iv_image_done.setImageResource(R.drawable.ic_task_done_main)

            loading_indicator.setIndeterminateDrawable(ThreeBounce())
            loading_indicator.visibility = View.GONE

            val sumJunk = intent.extras?.getString(Constant.Variable.SHARED_PREF_JUNK)
            tv_scanning.text = "$sumJunk MB of Junk Files Are Cleared"

            val anim = AnimatorInflater.loadAnimator(this, R.animator.flipping) as ObjectAnimator
            anim.target = iv_image_done
            anim.duration = 3000
            anim.start()

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {

                    val cache = intent.extras?.getString(Constant.Variable.SHARED_PREF_JUNK)
                    tv_scanning.text = "Cleared $cache MB"
                }

                override fun onAnimationEnd(animation: Animator) {
                    setupFinishCleaningJunk()
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

            tv_files_data.text = ""
        }, 8000)
    }

    private fun setupFinishCleaningJunk() {
        rippleBackground.stopRippleAnimation()
        Handler().postDelayed({ finish() }, 1000)
    }

    private fun startAnim(timePosition: Int) {
        when (timePosition) {
            1 -> {
                iv_ball_indicator_1.show()
                iv_ball_indicator_3.show()
                iv_ball_indicator_5.show()

                iv_ball_indicator_2.hide()
                iv_ball_indicator_4.hide()
                iv_ball_indicator_6.hide()
            }
            2 -> {
                iv_ball_indicator_2.show()
                iv_ball_indicator_4.show()
                iv_ball_indicator_6.show()

                iv_ball_indicator_1.hide()
                iv_ball_indicator_3.hide()
                iv_ball_indicator_5.hide()
            }
            3 -> {
                iv_ball_indicator_2.show()
                iv_ball_indicator_4.show()
                iv_ball_indicator_6.show()

                iv_ball_indicator_1.show()
                iv_ball_indicator_3.show()
                iv_ball_indicator_5.show()
            }
            4 -> {
                iv_ball_indicator_2.show()
                iv_ball_indicator_3.show()
                iv_ball_indicator_5.show()

                iv_ball_indicator_1.show()
                iv_ball_indicator_2.show()
                iv_ball_indicator_6.show()
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