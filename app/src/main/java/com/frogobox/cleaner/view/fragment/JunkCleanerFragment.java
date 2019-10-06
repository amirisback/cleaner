package com.frogobox.cleaner.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseFragment;
import com.frogobox.cleaner.service.AlarmJunkBroadcastReceiver;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.ScanningJunkActivity;

import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

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

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_junk_cleaner, container, false);
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

        try {

            sharedpreferences = getActivity().getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE);


            if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_JUNK, "1").equals("1")) {
                mainbrush.setImageResource(R.drawable.img_junktop_red);
                mainbutton.setImageResource(R.drawable.bg_button_clean);
                cache.setImageResource(R.drawable.img_junk_red_cache);
                temp.setImageResource(R.drawable.img_junk_red_temp);
                residue.setImageResource(R.drawable.img_junk_red_residual);
                system.setImageResource(R.drawable.img_junk_red_system);

                Random ran1 = new Random();
                final int proc1 = ran1.nextInt(20) + 5;

                Random ran2 = new Random();
                final int proc2 = ran2.nextInt(15) + 10;

                Random ran3 = new Random();
                final int proc3 = ran3.nextInt(30) + 15;

                Random ran4 = new Random();
                final int proc4 = ran4.nextInt(25) + 10;

                alljunk = proc1 + proc2 + proc3 + proc4;

                maintext.setText(alljunk + " MB");
                maintext.setTextColor(Color.parseColor("#F22938"));

                cachetext.setText(proc1 + " MB");
                cachetext.setTextColor(Color.parseColor("#F22938"));

                temptext.setText(proc2 + " MB");
                temptext.setTextColor(Color.parseColor("#F22938"));

                residuetext.setText(proc3 + " MB");
                residuetext.setTextColor(Color.parseColor("#F22938"));

                systemtext.setText(proc4 + " MB");
                systemtext.setTextColor(Color.parseColor("#F22938"));

            } else {
                mainbrush.setImageResource(R.drawable.img_junktop_blue);
                mainbutton.setImageResource(R.drawable.bg_button_cleaned);
                cache.setImageResource(R.drawable.img_junk_green_cache);
                temp.setImageResource(R.drawable.img_junk_green_temp);
                residue.setImageResource(R.drawable.img_junk_green_residual);
                system.setImageResource(R.drawable.img_junk_green_system);


                maintext.setText("CRYSTAL CLEAR");
                maintext.setTextColor(Color.parseColor("#24D149"));

                cachetext.setText(0 + " MB");
                cachetext.setTextColor(Color.parseColor("#24D149"));

                temptext.setText(0 + " MB");
                temptext.setTextColor(Color.parseColor("#24D149"));

                residuetext.setText(0 + " MB");
                residuetext.setTextColor(Color.parseColor("#24D149"));

                systemtext.setText(0 + " MB");
                systemtext.setTextColor(Color.parseColor("#24D149"));
            }


            mainbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_JUNK, "1").equals("1")) {

                        Intent i = new Intent(getActivity(), ScanningJunkActivity.class);
                        i.putExtra(Constant.Variable.SHARED_PREF_JUNK, alljunk + "");
                        startActivity(i);

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //Do something after 100ms


                                mainbrush.setImageResource(R.drawable.img_junktop_blue);
                                mainbutton.setImageResource(R.drawable.bg_button_cleaned);
                                cache.setImageResource(R.drawable.img_junk_green_cache);
                                temp.setImageResource(R.drawable.img_junk_green_temp);
                                residue.setImageResource(R.drawable.img_junk_green_residual);
                                system.setImageResource(R.drawable.img_junk_green_system);


                                maintext.setText("CRYSTAL CLEAR");
                                maintext.setTextColor(Color.parseColor("#24D149"));

                                cachetext.setText(0 + " MB");
                                cachetext.setTextColor(Color.parseColor("#24D149"));

                                temptext.setText(0 + " MB");
                                temptext.setTextColor(Color.parseColor("#24D149"));

                                residuetext.setText(0 + " MB");
                                residuetext.setTextColor(Color.parseColor("#24D149"));

                                systemtext.setText(0 + " MB");
                                systemtext.setTextColor(Color.parseColor("#24D149"));


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
                    } else {
//                        Toast.makeText(getActivity(), "No Junk Files ALready Cleaned.", Toast.LENGTH_LONG).show();

                        @SuppressLint("RestrictedApi") LayoutInflater inflater = getLayoutInflater(getArguments());
                        View layout = inflater.inflate(R.layout.toast_apps, null);

                        ImageView image = layout.findViewById(R.id.image);

                        TextView text = layout.findViewById(R.id.textView1);
                        text.setText("No Junk Files ALready Cleaned.");

                        Toast toast = new Toast(getActivity());
                        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            });


//            Random ran1 = new Random();
//            final int proc1 = ran1.nextInt(20) + 5;
//
//            Random ran2 = new Random();
//            final int proc2 = ran2.nextInt(15) + 10;
//
//            Random ran3 = new Random();
//            final int proc3 = ran3.nextInt(30) + 15;
//
//            Random ran4 = new Random();
//            final int proc4 = ran4.nextInt(25) + 10;
//
//            alljunk=proc1+proc2+proc3+proc4;
//
//            maintext.setText(alljunk+" MB");
//            maintext.setTextColor(Color.parseColor("#F22938"));
//
//            cachetext.setText(proc1+" MB");
//            cachetext.setTextColor(Color.parseColor("#F22938"));
//
//            temptext.setText(proc2+" MB");
//            temptext.setTextColor(Color.parseColor("#F22938"));
//
//            residuetext.setText(proc3+" MB");
//            residuetext.setTextColor(Color.parseColor("#F22938"));
//
//            systemtext.setText(proc4+" MB");
//            systemtext.setTextColor(Color.parseColor("#F22938"));

        } catch (Exception e) {

        }

        return view;
    }

}
