package com.frogobox.cleaner.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.frogobox.cleaner.R
import com.frogobox.cleaner.utils.Constant

/**
 * Created by Frogobox Software Industries 3/3/2017.
 */

class AppBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, arg1: Intent) {
        logicOnReceive(context, arg1)
    }

    private fun logicOnReceive(context: Context, arg1: Intent) {
        val data = arg1.data
        val installedPackageName = data!!.encodedSchemeSpecificPart

        if (installedPackageName != Constant.PACKAGE_NAME) {
            //            Toast.makeText(context, installedPackageName + "", Toast.LENGTH_SHORT).show();

            val packageManager = context.applicationContext.packageManager
            try {
                val appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(installedPackageName, PackageManager.GET_META_DATA)) as String
                val inflater = LayoutInflater.from(context)
                val layout = inflater.inflate(R.layout.toast_apps, null)
                val image = layout.findViewById<ImageView>(R.id.image)
                val text = layout.findViewById<TextView>(R.id.toast_tv)
                val notif = appName + " Is Optimized by" + context.getString(R.string.app_name)
                text.text = notif

                val toast = Toast(context)
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 120)
                toast.duration = Toast.LENGTH_LONG
                toast.view = layout
                toast.show()
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }
    }

}
