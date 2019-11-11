package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.view.adapter.ScanCpuAppsViewAdapter
import com.frogobox.cleaner.view.ui.fragment.CPUCoolerFragment
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.activity_cpu_scanner.*

class CPUScannerActivity : BaseActivity() {

    // Scan Cpu For Power Consuming and Over heating Apps

    private lateinit var mAdapter: ScanCpuAppsViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpu_scanner)

        setupAnimationIcon()
        setupRecyclerView()

    }

    private fun setupAnimationIcon() {
        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = 3
        rotate.interpolator = LinearInterpolator()
        scann.startAnimation(rotate)

        val animation = TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f)          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.duration = 5000  // animation duration
        animation.repeatCount = 0
        animation.interpolator = LinearInterpolator() // animation repeat count
        animation.fillAfter = true
        heart.startAnimation(animation)

        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {}

            override fun onAnimationEnd(animation: Animation) {
                heart.setImageResource(0)
                heart.setBackgroundResource(0)
            }

            override fun onAnimationRepeat(animation: Animation) {}
        })
    }

    private fun setupRecyclerView() {
        mAdapter = ScanCpuAppsViewAdapter(CPUCoolerFragment.apps)

        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = mAdapter

        mAdapter.notifyDataSetChanged()

        setupContentAdapter()
    }

    private fun setupContentAdapter() {
        try {

            Handler().postDelayed({ add("Limit Brightness Upto 80%", 0) }, 0)

            Handler().postDelayed({
                remove(0)
                add("Decrease Device Performance", 1)
            }, 900)

            Handler().postDelayed({
                remove(0)
                add("Close All Battery Consuming Apps", 2)
            }, 1800)

            Handler().postDelayed({
                remove(0)
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3)
            }, 2700)

            Handler().postDelayed({
                remove(0)
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 4)
            }, 3700)

            Handler().postDelayed({
                remove(0)
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5)
            }, 4400)

            Handler().postDelayed({
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6)
                remove(0)

                rippleBackground.startRippleAnimation()
                heart.setImageResource(0)
                heart.setBackgroundResource(0)
                cpu.setImageResource(R.drawable.circle_line_green)
                scann.setImageResource(R.drawable.img_scan_task_complete)
                val anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.flipping) as ObjectAnimator
                anim.target = scann
                anim.duration = 3000
                anim.start()

                rel.visibility = View.GONE

                cpucooler.text = "Cooled CPU to 25.3°C"
                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        heart.setImageResource(0)
                        heart.setBackgroundResource(0)
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        rippleBackground.stopRippleAnimation()
                        setupShowAdsInterstitial()
                        Handler().postDelayed({ finish() }, 1000)
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}

                })
            }, 5500)

        } catch (e: Exception) {

        }
    }

    private fun add(text: String, position: Int) {
        try {
            mAdapter.notifyItemInserted(position)

        } catch (e: Exception) {
        }

    }


    private fun remove(position: Int) {
        mAdapter.notifyItemRemoved(position)
        try {
            CPUCoolerFragment.apps.removeAt(position)
        } catch (e: Exception) {
        }
    }

}