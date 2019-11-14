package com.frogobox.cleaner.base

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.frogobox.cleaner.R

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.base
 *
 */
abstract class BaseFragment : Fragment() {

    lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = (activity as BaseActivity)
    }

    protected fun setupChildFragment(frameId: Int, fragment: Fragment){
        childFragmentManager.beginTransaction().apply {
            replace(frameId, fragment)
            commit()
        }
    }

    protected inline fun <reified ClassActivity> baseStartActivity() {
        context?.startActivity(Intent(context, ClassActivity::class.java))
    }

    protected inline fun <reified ClassActivity, Model> baseStartActivity(
            extraKey: String,
            data: Model
    ) {
        val intent = Intent(context, ClassActivity::class.java)
        val extraData = BaseHelper().baseToJson(data)
        intent.putExtra(extraKey, extraData)
        context?.startActivity(intent)
    }

    fun <Model> baseNewInstance(argsKey: String, data: Model){
        val argsData = BaseHelper().baseToJson(data)
        val bundleArgs = Bundle().apply {
            putString(argsKey, argsData)
        }
        this.arguments = bundleArgs
    }

    protected inline fun <reified Model> baseGetInstance(argsKey: String) : Model {
        val argsData = this.arguments?.getString(argsKey)
        val instaceData = BaseHelper().baseFromJson<Model>(argsData)
        return instaceData
    }

    protected fun checkArgument(argsKey: String) : Boolean{
        return arguments!!.containsKey(argsKey)
    }

    protected fun setupEventEmptyView(view: View, isEmpty: Boolean) {
        if (isEmpty) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    protected fun setupEventProgressView(view: View, progress: Boolean) {
        if (progress) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }

    protected fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    protected fun showCustomToast(message: String){
        val layout = LayoutInflater.from(context).inflate(R.layout.toast_apps, null)
        val text = layout.findViewById<TextView>(R.id.textView1)
        text.text = message
        val toast = Toast(context)
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70)
        toast.duration = Toast.LENGTH_LONG
        toast.view = layout
        toast.show()
    }

    protected fun setOptimizeButton(button: Button, text: Int){
        button.setBackgroundResource(R.drawable.bg_border_button_blue)
        button.setTextColor(mActivity.getColorRes(R.color.colorTextWhite))
        button.text = getString(text)
    }


    protected fun setDoneOptimizeButton(button: Button, text: Int){
        button.setBackgroundResource(R.drawable.bg_border_button_green)
        button.setTextColor(mActivity.getColorRes(R.color.colorPrimaryText))
        button.text = getString(text)
    }

}