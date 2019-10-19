package com.frogobox.cleaner.view.activity.batterysaver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.utils.Constant
import com.frogobox.cleaner.view.activity.PowerSavingComplitionActivity
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.activity_revert_to_normal.*

/**
 * Created by Frogobox Software Industries 2/21/2017.
 */

class NormalModeBatterySaverActivity : BaseActivity() {

    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var check = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revert_to_normal)

        sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE)
        editor = sharedpreferences!!.edit()

        // Call to Intersticial load
        setupShowAdsInterstitial()

        dynamicArcView2!!.addSeries(SeriesItem.Builder(getColorRes(R.color.colorBackgroundDarkBlackBlue))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build())

        //Create data series track
        val seriesItem1 = SeriesItem.Builder(getColorRes(R.color.colorBackgroundDarkBlackBlue))
                .setRange(0f, 100f, 0f)
                .setLineWidth(10f)
                .build()

        val seriesItem2 = SeriesItem.Builder(getColorRes(R.color.colorTextWhite))
                .setRange(0f, 100f, 0f)
                .setLineWidth(10f)
                .build()
        //        int series1Index = dynamicArcView2.addSeries(seriesItem1);
        val series1Index2 = dynamicArcView2!!.addSeries(seriesItem2)

        seriesItem2.addArcSeriesItemListener(object : SeriesItem.SeriesItemListener {
            override fun onSeriesItemAnimationProgress(v: Float, v1: Float) {

                val i = v1.toInt()
                completion!!.text = "$i%"

                if (v1 >= 10 && v1 < 50) {
                    ist!!.setTextColor(getColorRes(R.color.colorTextWhite))
                    istpic!!.setImageResource(R.drawable.circle_white)
                } else if (v1 >= 50 && v1 < 75) {
                    sec!!.setTextColor(getColorRes(R.color.colorTextWhite))
                    secpic!!.setImageResource(R.drawable.circle_white)
                } else if (v1 >= 75 && v1 < 90) {
                    thi!!.setTextColor(getColorRes(R.color.colorTextWhite))
                    thipic!!.setImageResource(R.drawable.circle_white)
                } else if (v1 >= 90 && v1 <= 100) {
                    fou!!.setTextColor(getColorRes(R.color.colorTextWhite))
                    foupic!!.setImageResource(R.drawable.circle_white)
                }

            }

            override fun onSeriesItemDisplayProgress(v: Float) {}
        })


        dynamicArcView2!!.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {}
                    override fun onEventEnd(decoEvent: DecoEvent) {}

                })
                .build())

        dynamicArcView2!!.addEvent(DecoEvent.Builder(100f).setIndex(series1Index2).setDelay(1000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {}
            override fun onEventEnd(decoEvent: DecoEvent) {
                try {
                    mInterstitialAd.show()
                } catch (e: Exception) {

                }
                check = 1
                youDesirePermissionCode(this@NormalModeBatterySaverActivity)
                editor!!.putString("mode", "0")
                editor!!.commit()

            }
        }).build())
    }

    fun enablesAll() {
        PowerSavingComplitionActivity.setAutoOrientationEnabled(applicationContext, true)
        Settings.System.putInt(this.contentResolver, Settings.System.SCREEN_BRIGHTNESS, 255)
        ContentResolver.setMasterSyncAutomatically(true)
    }

    fun youDesirePermissionCode(context: Activity) {
        // Run time permission for marshmallow users
        val permission: Boolean
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context)
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED
        }
        if (permission) {
            enablesAll()
            finish()
        } else {
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
                intent.data = Uri.parse("package:" + context.packageName)
                context.startActivityForResult(intent, 1)
            } else {
                ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.WRITE_SETTINGS), 1)
            }
        }
    }

    @SuppressLint("NewApi")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && Settings.System.canWrite(this)) {
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success")
            enablesAll()
            finish()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            enablesAll()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (check == 1) {
            try {
                enablesAll()
            } catch (e: Exception) {
                finish()
            }
            finish()
        }
    }
}
