package com.frogobox.cleaner.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseFragment;
import com.frogobox.cleaner.model.Time;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.NormalModeActivity;
import com.frogobox.cleaner.view.activity.PowerSavingPopupActivity;
import com.frogobox.cleaner.view.activity.UltraPopUpActivity;

import me.itangqi.waveloadingview.WaveLoadingView;

import static com.frogobox.cleaner.utils.Constant.Variable.COLOR_SEABLUE;

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
 *
 */

public class BatterySaverFragment extends BaseFragment {

    private WaveLoadingView mWaveLoadingView;
    private TextView hourn, minutes, hourp, minutep, houru, minutesu, hourmain, minutesmain;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            mWaveLoadingView.setProgressValue(level);
            mWaveLoadingView.setCenterTitle(level + "%");
//            mWaveLoadingView.setBottomTitle(level+"%");
            setupCheckBatteryLevel(level);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_battery_saver, container, false);
        mWaveLoadingView = (WaveLoadingView) view.findViewById(R.id.waveView);
        ImageView powersaving = view.findViewById(R.id.powersaving);
        ImageView ultrasaving = view.findViewById(R.id.ultra);
        ImageView normal = view.findViewById(R.id.normal);
        hourn = view.findViewById(R.id.hourn);
        minutes = view.findViewById(R.id.minutes);
        hourp = view.findViewById(R.id.hourp);
        minutep = view.findViewById(R.id.minutesp);
        houru = view.findViewById(R.id.houru);
        minutesu = view.findViewById(R.id.minutesu);
        hourmain = view.findViewById(R.id.hourmain);
        minutesmain = view.findViewById(R.id.minutesmain);
        sharedpreferences = getActivity().getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE);
        getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        try {

            powersaving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), PowerSavingPopupActivity.class);
                    i.putExtra(Constant.Variable.EXTRA_HOURS, hourp.getText());
                    i.putExtra(Constant.Variable.EXTRA_MINUTES, minutep.getText());
                    i.putExtra(Constant.Variable.EXTRA_MINUTES_NORMAL, minutes.getText());
                    i.putExtra(Constant.Variable.EXTRA_HOURS_NORMAL, hourn.getText());
                    startActivity(i);
                }
            });

            ultrasaving.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), UltraPopUpActivity.class);
                    i.putExtra(Constant.Variable.EXTRA_HOURS, houru.getText());
                    i.putExtra(Constant.Variable.EXTRA_MINUTES, minutesu.getText());
                    i.putExtra(Constant.Variable.EXTRA_MINUTES_NORMAL, minutes.getText());
                    i.putExtra(Constant.Variable.EXTRA_HOURS_NORMAL, hourn.getText());
                    startActivity(i);
                }
            });

            normal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), NormalModeActivity.class);
                    startActivity(i);
                }
            });


            mWaveLoadingView.setShapeType(WaveLoadingView.ShapeType.CIRCLE);
            mWaveLoadingView.setCenterTitleColor(mActivity.getColorRes(R.color.colorTextWhite));
            mWaveLoadingView.setBottomTitleColor(mActivity.getColorRes(R.color.colorTextWhite));
            mWaveLoadingView.setBorderWidth(10);
            mWaveLoadingView.setAmplitudeRatio(30);
            mWaveLoadingView.setWaveColor(Color.parseColor(COLOR_SEABLUE));
            mWaveLoadingView.setBorderColor(Color.parseColor("#000000"));
            mWaveLoadingView.setTopTitleStrokeColor(Color.BLUE);
            mWaveLoadingView.setTopTitleStrokeWidth(3);
            mWaveLoadingView.setAnimDuration(3000);
//        mWaveLoadingView.pauseAnimation();
//        mWaveLoadingView.resumeAnimation();
//        mWaveLoadingView.cancelAnimation();
            mWaveLoadingView.startAnimation();


        } catch (Exception e) {

        }

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onStop() {
        super.onStop();
        try {
            getActivity().unregisterReceiver(mBatInfoReceiver);
        } catch (Exception e) {

        }
    }

    private void setupTextViewTime(Time time){
        hourn.setText(time.getHourn() + "");
        minutes.setText(time.getMinutes() + "");

        hourp.setText(time.getHourp() + "");
        minutep.setText(time.getMinutep() + "");

        houru.setText(time.getHouru() + "");
        minutesu.setText(time.getMinutesu() + "");

        if (sharedpreferences.getString("mode", "0").equals("0")) {
            hourmain.setText(time.getHourn() + "");
            minutesmain.setText(time.getMinutes() + "");
        }

        if (sharedpreferences.getString("mode", "0").equals("1")) {
            hourmain.setText(time.getHourp() + "");
            minutesmain.setText(time.getMinutep() + "");
        }
    }

    private void setupCheckBatteryLevel(int level){
        if (level <= 5) {
            Time time = new Time(0, 15, 2, 25, 3, 55);
            setupTextViewTime(time);
        }

        if (5 < level && level <= 10) {
            Time time = new Time(0, 30, 3, 5, 6, 0);
            setupTextViewTime(time);
        }

        if (level > 10 && level <= 15) {
            Time time = new Time(0, 45, 3, 50, 8, 25);
            setupTextViewTime(time);
        }

        if (level > 15 && level <= 25) {
            Time time = new Time(1, 30, 4, 45, 12, 55);
            setupTextViewTime(time);
        }

        if (level > 25 && level <= 35) {
            Time time = new Time(2, 20, 6, 2, 19, 2);
            setupTextViewTime(time);
        }

        if (level > 35 && level <= 50) {
            Time time = new Time(5, 20, 9, 25, 22, 0);
            setupTextViewTime(time);
        }

        if (level > 50 && level <= 65) {
            Time time = new Time(7, 30, 11, 1, 28, 15);
            setupTextViewTime(time);
        }

        if (level > 65 && level <= 75) {
            Time time = new Time(9, 10, 14, 25, 30, 55);
            setupTextViewTime(time);
        }

        if (level > 75 && level <= 85) {
            Time time = new Time(14, 15, 17, 10, 38, 5);
            setupTextViewTime(time);
        }

        if (level > 85 && level <= 100) {
            Time time = new Time(20, 45, 30, 0, 60, 55);
            setupTextViewTime(time);
        }
    }

}
