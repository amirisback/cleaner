package com.frogobox.cleaner.view.activity.batterysaver;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseActivity;
import com.frogobox.cleaner.model.PowerItem;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.PowerSavingComplitionActivity;
import com.frogobox.cleaner.view.adapter.PowerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Frogobox Software Industries 2/19/2017.
 */

public class PowerSavingBatterySaverActivity extends BaseActivity {

    private PowerViewAdapter mAdapter;
    private List<PowerItem> items = new ArrayList<>();
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_powersaving_popup);

        Bundle extraBundles = getIntent().getExtras();

        SharedPreferences sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        TextView addedtime = findViewById(R.id.addedtime);
        TextView addedtimedetail = findViewById(R.id.addedtimedetail);

        int hour;
        int min;
        try {
            hour = Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_HOURS).replaceAll("[^0-9]", "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_HOURS_NORMAL).replaceAll("[^0-9]", ""));
            min = Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES).replaceAll("[^0-9]", "")) - Integer.parseInt(extraBundles.getString(Constant.Variable.EXTRA_MINUTES_NORMAL).replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            hour = 3;
            min = 5;
        }

        if (hour == 0 && min == 0) {
            hour = 3;
            min = 5;
        }

        addedtime.setText("(+" + hour + " h " + Math.abs(min) + " m)");
        addedtimedetail.setText("Extended Battery Up to " + Math.abs(hour) + "h " + Math.abs(min) + "m");

        ImageView applied = findViewById(R.id.applied);
        applied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("mode", "1");
                editor.commit();
                Intent i = new Intent(getApplicationContext(), PowerSavingComplitionActivity.class);
                startActivity(i);
                finish();
            }
        });
        
        setupRecyclerView();
        setupAdapterHandler();
        
    }
    
    private void setupRecyclerView(){
        mAdapter = new PowerViewAdapter(items);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.getItemAnimator().setAddDuration(200);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void setupAdapterHandler() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Limit Brightness Upto 80%", 0);
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Decrease Device Performance", 1);
            }
        }, 2000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Close All Battery Consuming Apps", 2);
            }
        }, 3000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3);

            }
        }, 4000);
    }

    private void add(String text, int position) {
        PowerItem item = new PowerItem(text);
        items.add(item);
        mAdapter.notifyItemInserted(position);
    }

}
