package com.frogobox.cleaner.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.frogobox.cleaner.R
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.fragment.ChargeBoosterFragment

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

class AlarmBoosterBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        logicOnReceive(context, intent)
    }

    private fun logicOnReceive(context: Context, intent: Intent) {

        val sharedpreferences = context.getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString(Constant.Variable.SHARED_PREF_BOOSTER, "1")
        editor.commit()

        try {
            ChargeBoosterFragment.optimizebutton.setBackgroundResource(0)
            ChargeBoosterFragment.optimizebutton.setImageResource(0)
            ChargeBoosterFragment.optimizebutton.setImageResource(R.drawable.bg_button_optimize)
        } catch (e: Exception) {

        }

    }

}
