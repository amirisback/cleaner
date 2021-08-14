package com.frogobox.cleaner.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.frogobox.cleaner.utils.Constant

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

class JunkBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val sharedpreferences = context.getSharedPreferences(Constant.SHARED_PREF_WASEEM, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        editor.putString(Constant.SHARED_PREF_JUNK, "1")
        editor.apply()

    }
}
