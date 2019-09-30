package com.frogobox.cleaner.view.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.frogobox.cleaner.myapplication.R;
import com.frogobox.cleaner.utils.Constant;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

/**
 * Created by Frogobox Software Industries 2/21/2017.
 */

public class NormalModeActivity extends Activity {

    private DecoView arcView;
    private TextView ist, sec, thir, fou, completion;
    private ImageView istpic, secpic, thirpic, foupic;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;
    private int check = 0;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revert_to_normal);

        ist = findViewById(R.id.ist);
        sec = findViewById(R.id.sec);
        thir = findViewById(R.id.thi);
        fou = findViewById(R.id.fou);
        istpic = findViewById(R.id.istpic);
        secpic = findViewById(R.id.secpic);
        thirpic = findViewById(R.id.thipic);
        foupic = findViewById(R.id.foupic);
        completion = findViewById(R.id.completion);

        sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WAS, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


        ///// Call to Intersticial load

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        AdRequest adRequestInter = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
        });
        mInterstitialAd.loadAd(adRequestInter);


        //// DEcoView Library For Progress Completion a circle Drrawn using this library


        arcView = (DecoView) findViewById(R.id.dynamicArcView2);

//        arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
//                .setRange(0, 100, 0)
//                .setInterpolator(new AccelerateInterpolator())
//                .build());

        arcView.addSeries(new SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(12f)
                .build());

//Create data series track
        SeriesItem seriesItem1 = new SeriesItem.Builder(Color.parseColor("#27282D"))
                .setRange(0, 100, 0)
                .setLineWidth(10f)
                .build();

        SeriesItem seriesItem2 = new SeriesItem.Builder(Color.parseColor(Constant.Variable.COLOR_WHITE))
                .setRange(0, 100, 0)
                .setLineWidth(10f)
                .build();
//
//        int series1Index = arcView.addSeries(seriesItem1);
        int series1Index2 = arcView.addSeries(seriesItem2);

        seriesItem2.addArcSeriesItemListener(new SeriesItem.SeriesItemListener() {
            @Override
            public void onSeriesItemAnimationProgress(float v, float v1) {


                Float obj = new Float(v1);
                int i = obj.intValue();
                completion.setText(i + "%");

                if (v1 >= 10 && v1 < 50) {
                    ist.setTextColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
                    istpic.setImageResource(R.drawable.circle_white);
//
//                    final InterstitialAd mInterstitialAd = new InterstitialAd(getApplicationContext());
//                    mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
//                    AdRequest adRequestInter = new AdRequest.Builder().build();
//                    mInterstitialAd.setAdListener(new AdListener() {
//                        @Override
//                        public void onAdLoaded() {
//                            mInterstitialAd.show();
//                        }
//                    });
//                    mInterstitialAd.loadAd(adRequestInter);

                } else if (v1 >= 50 && v1 < 75) {
                    sec.setTextColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
                    secpic.setImageResource(R.drawable.circle_white);
                } else if (v1 >= 75 && v1 < 90) {
                    thir.setTextColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
                    thirpic.setImageResource(R.drawable.circle_white);
                } else if (v1 >= 90 && v1 <= 100) {
                    fou.setTextColor(Color.parseColor(Constant.Variable.COLOR_WHITE));
                    foupic.setImageResource(R.drawable.circle_white);
                }


            }

            @Override
            public void onSeriesItemDisplayProgress(float v) {

            }
        });


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(0)
                .setDuration(0)
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

        arcView.addEvent(new DecoEvent.Builder(100).setIndex(series1Index2).setDelay(1000).setListener(new DecoEvent.ExecuteEventListener() {
            @Override
            public void onEventStart(DecoEvent decoEvent) {
//                bottom.setText("");
//                top.setText("");
//                centree.setText("Optimizing...");
            }

            @Override
            public void onEventEnd(DecoEvent decoEvent) {


                try {
                    mInterstitialAd.show();
                } catch (Exception e) {

                }


//                bottom.setText("Found");
//                top.setText("Storage");
//                Random ran3 = new Random();
//                ramperct.setText(ran3.nextInt(40) + 20+"%");

                check = 1;
                youDesirePermissionCode(NormalModeActivity.this);

//                enablesall();
                editor.putString("mode", "0");
                editor.commit();

            }
        }).build());
    }

    public void enablesall() {


        /// activate and release all system services


//        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        if (!mBluetoothAdapter.isEnabled()) {
//            mBluetoothAdapter.enable();
//        }

//        WifiManager wifiManager = (WifiManager) getApplication().getSystemService(Context.WIFI_SERVICE);
//
//
//        boolean wifiEnabled = wifiManager.isWifiEnabled();
//        if(wifiEnabled)
//        {
//            wifiManager.setWifiEnabled(false);
//        }

        PowerSavingComplitionActivity.setAutoOrientationEnabled(getApplicationContext(), true);

        Settings.System.putInt(this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 255);

        ContentResolver.setMasterSyncAutomatically(true);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }


    public void youDesirePermissionCode(Activity context) {

        //// Run time permission for marshmallow users

        boolean permission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = Settings.System.canWrite(context);
        } else {
            permission = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
//            Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 30);
//            setAutoOrientationEnabled(context, false);
        }
        if (permission) {
            //do your code
            enablesall();

            finish();
        } else {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                context.startActivityForResult(intent, 1);
            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.WRITE_SETTINGS}, 1);
            }
        }
    }

    //
    @SuppressLint("NewApi")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && Settings.System.canWrite(this)) {
            Log.d("TAG", "CODE_WRITE_SETTINGS_PERMISSION success");


//            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
            //do your code
            enablesall();

            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do your code

//            Toast.makeText(getApplicationContext(),"onRequestPermissionsResult",Toast.LENGTH_LONG).show();

            enablesall();

            finish();
        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        if (check == 1) {
            try {
                enablesall();
            } catch (Exception e) {
                finish();
            }
            finish();
        }
    }
}
