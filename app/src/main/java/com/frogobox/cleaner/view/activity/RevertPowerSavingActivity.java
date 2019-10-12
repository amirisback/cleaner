package com.frogobox.cleaner.view.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;



import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseActivity;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import static com.frogobox.cleaner.utils.Constant.Variable.COLOR_RED;
import static com.frogobox.cleaner.utils.Constant.Variable.COLOR_SEABLUE;

/**
 * Created by Frogobox Software Industries 2/21/2017.
 */

public class RevertPowerSavingActivity extends BaseActivity {

    /// Apply Normal Mode and Remove any power saving mode that was selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revert_powersaving);
        DecoView arcView = (DecoView) findViewById(R.id.dynamicArcView2);

        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                .setRange(0, 100, 0)
                .setInterpolator(new AccelerateInterpolator())
                .build());

        arcView.addSeries(new SeriesItem.Builder(Color.parseColor(COLOR_RED))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor(COLOR_RED))
                .setRange(0, 100, 0)
                .setLineWidth(12f)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor(COLOR_SEABLUE))
                .setRange(0, 100, 0)
                .setLineWidth(12f)
                .build();
//
//        int series1Index = arcView.addSeries(seriesItem1);
        int series1Index2 = arcView.addSeries(seriesItem2);


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(500)
                .setDuration(2000)
                .setListener(new DecoEvent.ExecuteEventListener() {
                    @Override
                    public void onEventStart(DecoEvent decoEvent) {
//                        bottom.setText("");
//                        top.setText("");
//                        centree.setText("Optimizing...");
                    }

                    @Override
                    public void onEventEnd(DecoEvent decoEvent) {

                    }
                })
                .build());

        arcView.addEvent(new DecoEvent.Builder(25).setIndex(series1Index2).setDelay(4000).setListener(new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent decoEvent) {
//                bottom.setText("");
//                top.setText("");
//                centree.setText("Optimizing...");
            }

            @Override
            public void onEventEnd(DecoEvent decoEvent) {
//                bottom.setText("Found");
//                top.setText("Storage");
//                Random ran3 = new Random();
//                ramperct.setText(ran3.nextInt(40) + 20+"%");
            }
        }).build());
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
