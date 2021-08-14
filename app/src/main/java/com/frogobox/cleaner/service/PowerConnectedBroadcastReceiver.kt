package com.frogobox.cleaner.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import com.frogobox.cleaner.mvvm.main.MainActivity

/**
 * Created by Frogobox Software Industries 3/14/2017.
 */

class PowerConnectedBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, MainActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)
    }
}
