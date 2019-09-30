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
import androidx.fragment.app.Fragment;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.activity.MainActivity;
import com.frogobox.cleaner.view.activity.NormalModeActivity;
import com.frogobox.cleaner.view.activity.PowerSavingPopupActivity;
import com.frogobox.cleaner.view.activity.UltraPopUpActivity;

import me.itangqi.waveloadingview.WaveLoadingView;

/**
 * Created by Frogobox Software Industries 2/12/2017.
 */

public class BatterySaverFragment extends Fragment {

    private WaveLoadingView mWaveLoadingView;
    private TextView hourn, minutes, hourp, minutep, houru, minutesu, hourmain, minutesmain;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            mWaveLoadingView.setProgressValue(level);
//            mWaveLoadingView.setBottomTitle(level+"%");
            mWaveLoadingView.setCenterTitle(level + "%");

            if (level <= 5) {
                hourn.setText(0 + "");
                minutes.setText(15 + "");

                hourp.setText(2 + "");
                minutep.setText(25 + "");

                houru.setText(3 + "");
                minutesu.setText(55 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(0 + "");
                    minutesmain.setText(15 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(2 + "");
                    minutesmain.setText(25 + "");
                }
            }
            if (level > 5 && level <= 10) {
                hourn.setText(0 + "");
                minutes.setText(30 + "");

                hourp.setText(3 + "");
                minutep.setText(5 + "");

                houru.setText(6 + "");
                minutesu.setText(0 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(0 + "");
                    minutesmain.setText(30 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(3 + "");
                    minutesmain.setText(5 + "");
                }
            }
            if (level > 10 && level <= 15) {
                hourn.setText(0 + "");
                minutes.setText(45 + "");

                hourp.setText(3 + "");
                minutep.setText(50 + "");

                houru.setText(8 + "");
                minutesu.setText(25 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(0 + "");
                    minutesmain.setText(45 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(3 + "");
                    minutesmain.setText(50 + "");
                }
            }
            if (level > 15 && level <= 25) {
                hourn.setText(1 + "");
                minutes.setText(30 + "");

                hourp.setText(4 + "");
                minutep.setText(45 + "");

                houru.setText(12 + "");
                minutesu.setText(55 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(1 + "");
                    minutesmain.setText(30 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(4 + "");
                    minutesmain.setText(45 + "");
                }
            }
            if (level > 25 && level <= 35) {
                hourn.setText(2 + "");
                minutes.setText(20 + "");

                hourp.setText(6 + "");
                minutep.setText(2 + "");

                houru.setText(19 + "");
                minutesu.setText(2 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(2 + "");
                    minutesmain.setText(20 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(6 + "");
                    minutesmain.setText(2 + "");
                }
            }
            if (level > 35 && level <= 50) {
                hourn.setText(5 + "");
                minutes.setText(20 + "");

                hourp.setText(9 + "");
                minutep.setText(25 + "");

                houru.setText(22 + "");
                minutesu.setText(0 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(5 + "");
                    minutesmain.setText(20 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(9 + "");
                    minutesmain.setText(20 + "");
                }
            }
            if (level > 50 && level <= 65) {
                hourn.setText(7 + "");
                minutes.setText(30 + "");

                hourp.setText(11 + "");
                minutep.setText(1 + "");

                houru.setText(28 + "");
                minutesu.setText(15 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(7 + "");
                    minutesmain.setText(30 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(11 + "");
                    minutesmain.setText(1 + "");
                }
            }
            if (level > 65 && level <= 75) {
                hourn.setText(9 + "");
                minutes.setText(10 + "");

                hourp.setText(14 + "");
                minutep.setText(25 + "");

                houru.setText(30 + "");
                minutesu.setText(55 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(9 + "");
                    minutesmain.setText(10 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(14 + "");
                    minutesmain.setText(25 + "");
                }
            }
            if (level > 75 && level <= 85) {
                hourn.setText(14 + "");
                minutes.setText(15 + "");

                hourp.setText(17 + "");
                minutep.setText(10 + "");

                houru.setText(38 + "");
                minutesu.setText(5 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(14 + "");
                    minutesmain.setText(15 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(17 + "");
                    minutesmain.setText(10 + "");
                }
            }
            if (level > 85 && level <= 100) {
                hourn.setText(20 + "");
                minutes.setText(45 + "");

                hourp.setText(30 + "");
                minutep.setText(0 + "");

                houru.setText(60 + "");
                minutesu.setText(55 + "");

                if (sharedpreferences.getString("mode", "0").equals("0")) {
                    hourmain.setText(20 + "");
                    minutesmain.setText(45 + "");
                }

                if (sharedpreferences.getString("mode", "0").equals("1")) {
                    hourmain.setText(30 + "");
                    minutesmain.setText(0 + "");
                }
            }

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
            mWaveLoadingView.setCenterTitleColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
            mWaveLoadingView.setBottomTitleColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
            mWaveLoadingView.setBorderWidth(10);
            mWaveLoadingView.setAmplitudeRatio(30);
            mWaveLoadingView.setWaveColor(Color.parseColor("#2499E0"));
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
//        MainActivity.name.setText("Battery Saver");
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


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            MainActivity.name.setText("Battery Saver");

        } else {

        }
    }


}
