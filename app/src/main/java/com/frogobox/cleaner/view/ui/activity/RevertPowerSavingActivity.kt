package com.frogobox.cleaner.view.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.view.animation.AccelerateInterpolator
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.hookedonplay.decoviewlib.charts.SeriesItem
import com.hookedonplay.decoviewlib.events.DecoEvent
import kotlinx.android.synthetic.main.activity_revert_powersaving.*

/**
 * Created by Frogobox Software Industries 2/21/2017.
 */

class RevertPowerSavingActivity : BaseActivity() {

    /// Apply Normal Mode and Remove any power saving mode that was selected

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_revert_powersaving)

        dynamicArcView2.addSeries(SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0f, 100f, 0f)
                .setInterpolator(AccelerateInterpolator())
                .build())

        dynamicArcView2.addSeries(SeriesItem.Builder(getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 100f)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build())

        // Create data series track
        val seriesItem1 = SeriesItem.Builder(getColorRes(R.color.colorBackgroundRed))
                .setRange(0f, 100f, 0f)
                .setLineWidth(12f)
                .build()

        val seriesItem2 = SeriesItem.Builder(getColorRes(R.color.colorBackgroundSeaBlue))
                .setRange(0f, 100f, 0f)
                .setLineWidth(12f)
                .build()

        //        int series1Index = dynamicArcView2.addSeries(seriesItem1);
        val series1Index2 = dynamicArcView2.addSeries(seriesItem2)


        dynamicArcView2.addEvent(DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(2000)
                .setListener(object : DecoEvent.ExecuteEventListener {
                    override fun onEventStart(decoEvent: DecoEvent) {
                        //                        bottom.setText("");
                        //                        top.setText("");
                        //                        centree.setText("Optimizing...");
                    }

                    override fun onEventEnd(decoEvent: DecoEvent) {

                    }
                })
                .build())

        dynamicArcView2.addEvent(DecoEvent.Builder(25f).setIndex(series1Index2).setDelay(4000).setListener(object : DecoEvent.ExecuteEventListener {
            override fun onEventStart(decoEvent: DecoEvent) {
                //                bottom.setText("");
                //                top.setText("");
                //                centree.setText("Optimizing...");
            }

            override fun onEventEnd(decoEvent: DecoEvent) {
                //                bottom.setText("Found");
                //                top.setText("Storage");
                //                Random ran3 = new Random();
                //                ramperct.setText(ran3.nextInt(40) + 20+"%");
            }
        }).build())
    }

}
