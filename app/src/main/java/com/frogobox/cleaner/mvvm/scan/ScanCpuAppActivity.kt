package com.frogobox.cleaner.mvvm.scan

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
import com.frogobox.cleaner.core.BaseActivity
import com.frogobox.cleaner.databinding.ActivityCpuCoolerBinding
import com.frogobox.cleaner.mvvm.cpu.CpuCoolerFragment
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class ScanCpuAppActivity : BaseActivity<ActivityCpuCoolerBinding>() {

    override fun setupViewBinding(): ActivityCpuCoolerBinding {
        return ActivityCpuCoolerBinding.inflate(layoutInflater)
    }

    override fun setupViewModel() {
    }

    override fun setupUI(savedInstanceState: Bundle?) {
        setupAnimationProcess()
        setupRecyclerViewApps()
    }


    private fun setupAnimationProcess() {
        val rotate = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotate.duration = 1500
        rotate.repeatCount = 3
        rotate.interpolator = LinearInterpolator()
        binding.ivScanningMain.startAnimation(rotate)
    }

    private fun setupRecyclerViewApps() {

        binding.apply {

            val scanCpuAppsViewAdapter = ScanCpuAppAdapter(CpuCoolerFragment.apps)
            val mLayoutManager = LinearLayoutManager(this@ScanCpuAppActivity, LinearLayoutManager.HORIZONTAL, false)

            recyclerView.apply {
                itemAnimator = SlideInLeftAnimator()
                layoutManager = mLayoutManager
                itemAnimator = SlideInUpAnimator(OvershootInterpolator(1f))
                computeHorizontalScrollExtent()
                adapter = scanCpuAppsViewAdapter
            }

            scanCpuAppsViewAdapter.notifyDataSetChanged()
            setupContentAdapter(scanCpuAppsViewAdapter)
        }

    }

    private fun setupContentAdapter(adapter: ScanCpuAppAdapter) {
        Handler().postDelayed({ add(adapter, 0) }, 0)
        var timeDelay = 900
        for (i in 1..5) {
            Handler().postDelayed({ changeAppsItem(adapter, i) }, timeDelay.toLong())
            timeDelay += 850
        }

        Handler().postDelayed({

            binding.apply {
                ivImageDone.setImageResource(R.drawable.ic_task_done_main)
                changeAppsItem(adapter, 6)
                rippleBackground.startRippleAnimation()

                val animationWaterRiple = AnimatorInflater.loadAnimator(this@ScanCpuAppActivity, R.animator.flipping) as ObjectAnimator
                animationWaterRiple.target = ivImageDone
                animationWaterRiple.duration = 3000
                animationWaterRiple.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animation: Animator) {
                        ivScanningMain.visibility = View.GONE
                        tvCoolingCpu.text = "Cooled CPU to 25.3°C"
                    }

                    override fun onAnimationEnd(animation: Animator) {
                        finishCondition()
                    }

                    override fun onAnimationCancel(animation: Animator) {}
                    override fun onAnimationRepeat(animation: Animator) {}
                })

                animationWaterRiple.start()
            }

        }, 5500)
    }

    private fun finishCondition() {
        binding.rippleBackground.stopRippleAnimation()
        Handler().postDelayed({ finish() }, 1000)
    }

    private fun changeAppsItem(adapter: ScanCpuAppAdapter, position: Int) {
        remove(adapter)
        add(adapter, position)
    }

    private fun add(adapter: ScanCpuAppAdapter, position: Int) {
        adapter.notifyItemInserted(position)
    }

    private fun remove(adapter: ScanCpuAppAdapter) {
        adapter.notifyItemRemoved(0)
        CpuCoolerFragment.apps.removeAt(0)
    }

}