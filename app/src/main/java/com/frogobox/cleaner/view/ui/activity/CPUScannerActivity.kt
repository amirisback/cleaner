package com.frogobox.cleaner.view.ui.activity

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.ObjectAnimator
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
        iv_scanning_main.startAnimation(rotate)
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
        var timeDelay = 900
        for (i in 1..5) {
            Handler().postDelayed({ changeAppsItem(adapter, i) }, timeDelay.toLong())
            timeDelay += 850
        }

        Handler().postDelayed({

            iv_image_done.setImageResource(R.drawable.ic_task_done_main)
            changeAppsItem(adapter, 6)
            rippleBackground.startRippleAnimation()

            val animationWaterRiple = AnimatorInflater.loadAnimator(this, R.animator.flipping) as ObjectAnimator
            animationWaterRiple.target = iv_image_done
            animationWaterRiple.duration = 3000
            animationWaterRiple.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    iv_scanning_main.visibility = View.GONE
                    tv_cooling_cpu.text = "Cooled CPU to 25.3°C"
                }
                override fun onAnimationEnd(animation: Animator) { finishCondition() }
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })

            animationWaterRiple.start()

        }, 5500)
    }

    private fun finishCondition() {
        rippleBackground.stopRippleAnimation()
        Handler().postDelayed({ finish() }, 1000)
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