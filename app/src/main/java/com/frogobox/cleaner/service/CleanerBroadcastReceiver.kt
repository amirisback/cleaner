package com.frogobox.cleaner.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.frogobox.cleaner.utils.Constant

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

class CleanerBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        logicOnReceive(context, intent)
    }

    private fun logicOnReceive(context: Context, intent: Intent) {

        val sharedpreferences = context.getSharedPreferences(Constant.SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString(Constant.SHARED_PREF_BOOSTER, "1")
        editor.apply()

//        try {
//            ChargeBoosterFragment.optimizeButton.setBackgroundResource(0)
//            ChargeBoosterFragment.optimizeButton.setImageResource(0)
//            ChargeBoosterFragment.optimizeButton.setImageResource(R.drawable.bg_button_optimize)
//        } catch (e: Exception) {
//
//        }

    }

}
