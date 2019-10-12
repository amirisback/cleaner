package com.frogobox.cleaner.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseFragment;
import com.frogobox.cleaner.model.Apps;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.CpuScannerActivity;
import com.frogobox.cleaner.view.adapter.CPUCoolerViewAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.base
 */

public class CPUCoolerFragment extends BaseFragment {

    public static List<Apps> apps;
    private TextView batterytemp, showmain, showsec, nooverheating;
    private float temp;
    private ImageView coolbutton, tempimg;
    private RecyclerView recyclerView;
    private CPUCoolerViewAdapter mAdapter;
    private int check = 0;

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                int level = intent.getIntExtra("level", 0);
                temp = ((float) intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0)) / 10;

                batterytemp.setText(temp + "°C");

                if (temp >= 30.0) {
                    apps = new ArrayList<>();
                    tempimg.setImageResource(R.drawable.img_cooler_red);
                    showmain.setText("OVERHEATED");
                    showmain.setTextColor(Color.parseColor("#F22938"));
                    showsec.setText("Apps are causing problem hit cool down");
                    nooverheating.setText("");
                    coolbutton.setImageResource(R.drawable.bg_button_cool_blue);
                    coolbutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            setupCoolingBattery();
                        }
                    });

                    setupAndroidVersion();
                    recyclerView.setItemAnimator(new SlideInLeftAnimator());

//                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                    recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

                    recyclerView.getItemAnimator().setAddDuration(10000);
                    mAdapter = new CPUCoolerViewAdapter(apps);
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
                    recyclerView.computeHorizontalScrollExtent();
                    recyclerView.setAdapter(mAdapter);
                    getAppsIcon();
//                    recyclerView.getItemAnimator().setRemoveDuration(1000);
//                    recyclerView.getItemAnimator().setMoveDuration(1000);
//                    recyclerView.getItemAnimator().setChangeDuration(1000);

                }
            } catch (Exception e) {

            }

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cpu_cooler, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponent(view);
        setupCheckCooledBattery();
    }

    private void setupCheckCooledBattery(){
        try {
            getActivity().registerReceiver(batteryReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            setupBatteryCooled();
            Log.e("Temperrature", temp + "");
        } catch (Exception e) {

        }
    }

    private void setupCoolingBattery(){
        Intent i = new Intent(getActivity(), CpuScannerActivity.class);
        startActivity(i);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                getActivity().unregisterReceiver(batteryReceiver);
                setupBatteryCooled();
                batterytemp.setText("25.3" + "°C");
                recyclerView.setAdapter(null);

            }
        }, 2000);
    }

    private void setupBatteryCooled() {
        showsec.setTextColor(Color.parseColor("#24D149"));
        showmain.setTextColor(Color.parseColor("#24D149"));
        nooverheating.setText("Currently No App causing Overheating");
        showmain.setText("NORMAL");
        showsec.setText("CPU Temperature is GOOD");
        tempimg.setImageResource(R.drawable.img_cooler_blue);
        coolbutton.setImageResource(R.drawable.bg_button_cooled);
        coolbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomToast(getString(R.string.toast_cpu_normal_temperature));
            }
        });
    }

    private void setupComponent(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        tempimg = view.findViewById(R.id.tempimg);
        showmain = view.findViewById(R.id.showmain);
        showsec = view.findViewById(R.id.showsec);
        coolbutton = view.findViewById(R.id.coolbutton);
        nooverheating = view.findViewById(R.id.nooverheating);
        batterytemp = view.findViewById(R.id.batterytemp);
    }

    private void setupAndroidVersion() {
        if (Build.VERSION.SDK_INT < 23) {
            showsec.setTextAppearance(getContext(), android.R.style.TextAppearance_Medium);
            showsec.setTextColor(Color.parseColor("#F22938"));
        } else {
            showsec.setTextAppearance(android.R.style.TextAppearance_Small);
            showsec.setTextColor(Color.parseColor("#F22938"));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(batteryReceiver);
        } catch (Exception e) {

        }
    }

    private void getAppsIcon() {

        PackageManager pm = getActivity().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        if (packages != null) {
            for (int k = 0; k < packages.size(); k++) {
                String packageName = packages.get(k).packageName;
                Log.e("packageName-->", "" + packageName);

                if (!packageName.equals(Constant.Variable.PACKAGE_NAME)) {
                    Drawable ico = null;
                    try {
                        String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                        Apps app = new Apps();
                        File file = new File(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA).publicSourceDir);
                        long size = file.length();
                        Log.e("SIZE", size / 1000000 + "");
                        app.setSize(size / 1000000 + 20 + "MB");
                        ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
                        app.setImage(ico = getActivity().getPackageManager().getApplicationIcon(packages.get(k).packageName));
                        getActivity().getPackageManager();
                        Log.e("ico-->", "" + ico);

                        if (((a.flags & ApplicationInfo.FLAG_SYSTEM) == 0)) {
                            if (check <= 5) {
                                check++;
                                apps.add(app);
                            } else {
                                getActivity().unregisterReceiver(batteryReceiver);
                                break;
                            }

                        }
                        mAdapter.notifyDataSetChanged();

                    } catch (PackageManager.NameNotFoundException e) {
                        Log.e("ERROR", "Unable to find icon for package '"
                                + packageName + "': " + e.getMessage());
                    }
                }
            }

        }

        if (apps.size() > 1) {
            mAdapter = new CPUCoolerViewAdapter(apps);
            mAdapter.notifyDataSetChanged();
        }


    }

}