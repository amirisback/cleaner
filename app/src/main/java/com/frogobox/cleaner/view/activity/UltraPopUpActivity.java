package com.frogobox.cleaner.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.model.PowerItem;
import com.frogobox.cleaner.myapplication.R;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.adapter.PowerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Frogobox Software Industries 2/19/2017.
 */

public class UltraPopUpActivity extends Activity {

    private PowerViewAdapter mAdapter;
    private List<PowerItem> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getIntent().getExtras();
        setContentView(R.layout.activity_ultra_popup);
        ImageView applied = findViewById(R.id.applied);
        TextView extendedtime = findViewById(R.id.addedtime);
        TextView extendedtimedetail = findViewById(R.id.addedtimedetail);


        int hour;
        int min;
        try {

            hour = Integer.parseInt(b.getString(Constant.Variable.EXTRA_HOURS).replaceAll("[^0-9]", "")) - Integer.parseInt(b.getString(Constant.Variable.EXTRA_HOURS_NORMAL).replaceAll("[^0-9]", ""));
            min = Integer.parseInt(b.getString(Constant.Variable.EXTRA_MINUTES).replaceAll("[^0-9]", "")) - Integer.parseInt(b.getString(Constant.Variable.EXTRA_MINUTES_NORMAL).replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            hour = 4;
            min = 7;
        }

        if (hour == 0 && min == 0) {
            hour = 4;
            min = 7;
        }

        extendedtime.setText("(+" + hour + " h " + Math.abs(min) + " m)");
        extendedtimedetail.setText("Extended Battery Up to " + Math.abs(hour) + "h " + Math.abs(min) + "m");

        applied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(UltraPopUpActivity.this, ApplyingUltraActivity.class);
                startActivity(i);
//                if (Build.VERSION.SDK_INT >= 21) {
//                    getWindow().setNavigationBarColor(Color.parseColor("#000000"));
//                    getWindow().setStatusBarColor(Color.parseColor("#000000"));
//                }

                finish();

            }
        });

        items = new ArrayList<>();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

        recyclerView.getItemAnimator().setAddDuration(200);

        mAdapter = new PowerViewAdapter(items);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Limit Brightness Upto 90%", 0);


            }
        }, 1000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Decrease Device Performance", 1);


            }
        }, 2000);

        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Close All Battery Consuming Apps", 2);


            }
        }, 3000);

        final Handler handler4 = new Handler();
        handler4.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Use Black and White Scheme To Avoid Battery Draning", 3);
            }
        }, 4000);


        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Block Acess to Memory and Battery Draning Apps", 4);

            }
        }, 5000);

        final Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

            }
        }, 6000);

//        final Handler handler6 = new Handler();
//        handler4.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                add("Use Black and White Scheme To Avoid Battery Draning", 3);
//            }
//        }, 4000);


    }

    public void add(String text, int position) {
        PowerItem item = new PowerItem();
        item.setText(text);
        items.add(item);
//        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position);

    }

//    if(position==4)
//    {
//        mAdapter.notifyItemMoved(4,0);
//    }
//    else  if(position==5)
//    {
//        mAdapter.notifyItemMoved(5,0);
//    }

//    @Override
//    public void onBackPressed() {
////        super.onBackPressed();
//    }

}
