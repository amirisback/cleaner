package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
import android.os.Bundle
import android.os.Handler
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cpu_scanner)

        setupAnimationProcess()
        setupRecyclerViewApps()

    }

    private fun setupAnimationProcess() {
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

    private fun setupRecyclerViewApps() {
        val scanCpuAppsViewAdapter = ScanCpuAppsViewAdapter(CPUCoolerFragment.apps)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        recycler_view.itemAnimator = SlideInLeftAnimator()
        recycler_view.layoutManager = mLayoutManager
        recycler_view.itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
        recycler_view.computeHorizontalScrollExtent()
        recycler_view.adapter = scanCpuAppsViewAdapter
        scanCpuAppsViewAdapter.notifyDataSetChanged()

        setupContentAdapter(scanCpuAppsViewAdapter)
    }

    private fun setupContentAdapter(adapter: ScanCpuAppsViewAdapter) {
        Handler().postDelayed({ add(adapter, 0) }, 0)
        Handler().postDelayed({ changeAppsItem(adapter, 1) }, 900)
        Handler().postDelayed({ changeAppsItem(adapter, 2) }, 1800)
        Handler().postDelayed({ changeAppsItem(adapter, 3) }, 2700)
        Handler().postDelayed({ changeAppsItem(adapter, 4) }, 3700)
        Handler().postDelayed({ changeAppsItem(adapter, 5) }, 4400)

        Handler().postDelayed({
            changeAppsItem(adapter, 6)

            rippleBackground.startRippleAnimation()
            heart.setImageResource(0)
            heart.setBackgroundResource(0)
            cpu.setImageResource(R.drawable.circle_line_green)
            scann.setImageResource(R.drawable.img_scan_task_complete)

            val anim = AnimatorInflater.loadAnimator(applicationContext, R.animator.flipping) as ObjectAnimator
            anim.target = scann
            anim.duration = 3000
            anim.start()

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
    }

    private fun changeAppsItem(adapter: ScanCpuAppsViewAdapter, position: Int) {
        remove(adapter)
        add(adapter, position)
    }

    private fun add(adapter: ScanCpuAppsViewAdapter, position: Int) {
        adapter.notifyItemInserted(position)
    }

    private fun remove(adapter: ScanCpuAppsViewAdapter) {
        adapter.notifyItemRemoved(0)
        CPUCoolerFragment.apps.removeAt(0)
    }

}