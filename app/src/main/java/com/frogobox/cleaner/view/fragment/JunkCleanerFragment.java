package com.frogobox.cleaner.view.fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseFragment;
import com.frogobox.cleaner.service.AlarmJunkBroadcastReceiver;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.ScanningJunkActivity;

import java.util.Random;

import static android.content.Context.ALARM_SERVICE;
import static com.frogobox.cleaner.utils.Constant.Variable.COLOR_GREEN;
import static com.frogobox.cleaner.utils.Constant.Variable.COLOR_RED;
import static com.frogobox.cleaner.utils.Constant.Variable.ZERO_MB;
import static com.frogobox.cleaner.utils.Constant.Variable._MB;

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

public class JunkCleanerFragment extends BaseFragment {

    private ImageView mainbutton;
    private ImageView mainbrush, cache, temp, residue, system;
    private TextView maintext, cachetext, temptext, residuetext, systemtext;
    private int checkvar = 0;
    private int alljunk;

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_junk_cleaner, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupComponent(view);
        setupCheckJunk();
    }

    private void setupCheckJunk() {
        try {
            sharedpreferences = getActivity().getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE);
            if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_JUNK, "1").equals("1")) {
                setupDirtyFromJunk();
            } else {
                setupCleanFromJunk();
            }
            mainbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_JUNK, "1").equals("1")) {
                        setupDoInClenerJunk();
                    } else {
                        showCustomToast(getString(R.string.toast_cleaned_junk));
                    }
                }
            });
        } catch (Exception e) {
        }
    }

    private void setupText(TextView textView, String text, int color) {
        textView.setText(text);
        textView.setTextColor(color);
    }

    private void setupDoInClenerJunk() {

        Intent i = new Intent(getActivity(), ScanningJunkActivity.class);
        i.putExtra(Constant.Variable.SHARED_PREF_JUNK, alljunk + "");
        startActivity(i);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                setupCleanFromJunk();
                editor = sharedpreferences.edit();
                editor.putString(Constant.Variable.SHARED_PREF_JUNK, "0");
                editor.commit();
                Intent intent = new Intent(getActivity(), AlarmJunkBroadcastReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 0,
                        intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (600 * 1000), pendingIntent);
            }
        }, 2000);
    }

    private void setupComponent(View view) {
        mainbrush = view.findViewById(R.id.mainbrush);
        mainbutton = view.findViewById(R.id.mainbutton);
        cache = view.findViewById(R.id.cache);
        temp = view.findViewById(R.id.temp);
        residue = view.findViewById(R.id.residue);
        system = view.findViewById(R.id.system);

        maintext = view.findViewById(R.id.maintext);
        cachetext = view.findViewById(R.id.cachetext);
        temptext = view.findViewById(R.id.temptext);
        residuetext = view.findViewById(R.id.residuetext);
        systemtext = view.findViewById(R.id.systemtext);
    }

    private void setupDirtyFromJunk() {
        mainbrush.setImageResource(R.drawable.img_junktop_red);
        mainbutton.setImageResource(R.drawable.bg_button_clean);
        cache.setImageResource(R.drawable.img_junk_red_cache);
        temp.setImageResource(R.drawable.img_junk_red_temp);
        residue.setImageResource(R.drawable.img_junk_red_residual);
        system.setImageResource(R.drawable.img_junk_red_system);

        Random ran1 = new Random();
        Random ran2 = new Random();
        Random ran3 = new Random();
        Random ran4 = new Random();

        int proc1 = ran1.nextInt(20) + 5;
        int proc2 = ran2.nextInt(15) + 10;
        int proc3 = ran3.nextInt(30) + 15;
        int proc4 = ran4.nextInt(25) + 10;
        alljunk = proc1 + proc2 + proc3 + proc4;

        String mainString = alljunk + _MB;
        String cacheString = proc1 + _MB;
        String tempString = proc2 + _MB;
        String residueString = proc3 + _MB;
        String systemString = proc4 + _MB;

        int colorText = Color.parseColor(COLOR_RED);

        setupText(maintext, mainString, colorText);
        setupText(cachetext, cacheString, colorText);
        setupText(temptext, tempString, colorText);
        setupText(residuetext, residueString, colorText);
        setupText(systemtext, systemString, colorText);

    }

    private void setupCleanFromJunk() {
        mainbrush.setImageResource(R.drawable.img_junktop_blue);
        mainbutton.setImageResource(R.drawable.bg_button_cleaned);
        cache.setImageResource(R.drawable.img_junk_green_cache);
        temp.setImageResource(R.drawable.img_junk_green_temp);
        residue.setImageResource(R.drawable.img_junk_green_residual);
        system.setImageResource(R.drawable.img_junk_green_system);

        int color = Color.parseColor(COLOR_GREEN);

        setupText(maintext, "CRYSTAL CLEAR", color);
        setupText(cachetext, ZERO_MB, color);
        setupText(temptext, ZERO_MB, color);
        setupText(residuetext, ZERO_MB, color);
        setupText(systemtext, ZERO_MB, color);
    }

}