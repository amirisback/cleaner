package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.app.ActivityManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.model.Apps
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.adapter.JunkAppsViewAdapter
import com.frogobox.cleaner.view.adapter.SimpleDividerItemDecoration
import com.github.ybq.android.spinkit.style.ThreeBounce
import com.skyfishjy.library.RippleBackground
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
    private lateinit var mAdapter: JunkAppsViewAdapter
    private lateinit var apps: MutableList<Apps>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scanning_junk)

        val junk = intent.extras
        val timer = Timer()
        val mActivityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        apps = ArrayList()

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
        packages = packageManager.getInstalledApplications(0)

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


        val recyclerView = findViewById<View>(R.id.recycler_view) as RecyclerView

        recyclerView.itemAnimator = SlideInLeftAnimator()
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(this))
        mAdapter = JunkAppsViewAdapter(apps)
        val mLayoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recyclerView.computeHorizontalScrollExtent()
        recyclerView.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(this))

        Handler().postDelayed({ add("Limit Brightness Upto 80%", 0) }, 1000)
        Handler().postDelayed({ add("Decrease Device Performance", 1) }, 2000)
        Handler().postDelayed({ add("Close All Battery Consuming Apps", 2) }, 3000)
        Handler().postDelayed({ add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3) }, 4000)
        Handler().postDelayed({ remove(0) }, 5000)
        Handler().postDelayed({ remove(0) }, 6000)
        Handler().postDelayed({ remove(0) }, 7000)

        Handler().postDelayed({
            remove(0)

            val rippleBackground = findViewById<View>(R.id.content) as RippleBackground
            val imageView = findViewById<ImageView>(R.id.centerImage)
            rippleBackground.startRippleAnimation()
            //                front.setImageResource(0);
            //                imageView.setImageResource(0);
            //                front.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.task_complete));
            //                imageView.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.circle_line_green));
            front.setImageResource(R.drawable.img_scan_task_complete)
            back.setImageResource(R.drawable.circle_line_green)


            spin_kit.setIndeterminateDrawable(ThreeBounce())
            spin_kit.visibility = View.GONE

            scanning.setPadding(20, 0, 0, 0)

            if (Build.VERSION.SDK_INT < 23) {
                scanning.setTextAppearance(applicationContext, android.R.style.TextAppearance_Medium)
                scanning.text = junk?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared"
            } else {
                scanning.setTextAppearance(android.R.style.TextAppearance_Medium)
                scanning.text = junk?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared"
            }

            val anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.flipping) as ObjectAnimator
            anim.target = front
            anim.duration = 3000
            anim.start()

            anim.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    scanning.text = "Cleared " + junk?.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB"
                    scanning.setTextColor(getColorRes(R.color.colorTextWhite))
                }

                override fun onAnimationEnd(animation: Animator) {
                    setupShowAdsInterstitial()
                    rippleBackground.stopRippleAnimation()
                    Handler().postDelayed({ finish() }, 1000)
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}

            })

            files.text = ""
        }, 8000)

    }

    private fun startAnim(i1: Int) {
        if (i1 == 1) {
            scan1.show()
            scan3.show()
            scan5.show()

            scan2.hide()
            scan4.hide()
            scan6.hide()
        } else if (i1 == 2) {
            scan2.show()
            scan4.show()
            scan6.show()

            scan1.hide()
            scan3.hide()
            scan5.hide()
        } else if (i1 == 3) {
            scan2.show()
            scan4.show()
            scan6.show()

            scan1.show()
            scan3.show()
            scan5.show()
        } else if (i1 == 4) {
            scan2.show()
            scan3.show()
            scan5.show()

            scan1.show()
            scan2.show()
            scan6.show()
        }
        // or avi.smoothToShow();
    }
    
    fun add(text: String, position: Int) {

        val p = 0 + (Math.random() * (packages.size - 1 - 0 + 1)).toInt()
        var ico: Drawable? = null
        val item = Apps()

        val packageName = packages[p].packageName
        try {
            val pName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)) as String
            val a = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            ico = packageManager.getApplicationIcon(packages[p].packageName)
            item.image = ico
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        item.size = packages[p].dataDir
        apps.add(item)
        //        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position)
    }

    fun remove(position: Int) {
        //        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position)
        apps.removeAt(position)
    }

}