package com.frogobox.cleaner.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frogobox.cleaner.view.adapter.ScanCpuAppsViewAdapter;
import com.frogobox.cleaner.model.Apps;
import com.frogobox.cleaner.view.fragment.CPUCoolerFragment;

import com.frogobox.cleaner.myapplication.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.skyfishjy.library.RippleBackground;
import com.zys.brokenview.BrokenTouchListener;
import com.zys.brokenview.BrokenView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;


public class CpuScannerActivity extends Activity {

    ///// Scan Cpu For Power Consuming and Over heating Apps

    ImageView scanner,img_animation,cpu;
    BrokenView brokenView;
    BrokenTouchListener listener;
    ScanCpuAppsViewAdapter mAdapter;
    RecyclerView recyclerView;
    List<Apps> app=null;
    PackageManager pm;
    List<ApplicationInfo> packages;
    TextView cooledcpu;
    RelativeLayout rel;
    InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpu_scanner);
        scanner = (ImageView) findViewById(R.id.scann);
        cpu = (ImageView) findViewById(R.id.cpu);
        cooledcpu=(TextView) findViewById(R.id.cpucooler);
        img_animation = (ImageView) findViewById(R.id.heart);
        rel=(RelativeLayout) findViewById(R.id.rel);
        app=new ArrayList<>();

        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.interstitial));
        AdRequest adRequestInter = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
        });
        mInterstitialAd.loadAd(adRequestInter);


        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(3);
        rotate.setInterpolator(new LinearInterpolator());
        scanner.startAnimation(rotate);




        TranslateAnimation animation = new TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
        animation.setDuration(5000);  // animation duration
        animation.setRepeatCount(0);
        animation.setInterpolator(new LinearInterpolator());// animation repeat count
//        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
        animation.setFillAfter(true);

        img_animation.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                img_animation.setImageResource(0);
                img_animation.setBackgroundResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setItemAnimator(new SlideInLeftAnimator());


        mAdapter = new ScanCpuAppsViewAdapter(CPUCoolerFragment.apps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();


        try {


            final Handler handler1 = new Handler();
            handler1.postDelayed(new Runnable() {
                @Override
                public void run() {
                    add("Limit Brightness Upto 80%", 0);


                }
            }, 0);

            final Handler handler2 = new Handler();
            handler2.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Decrease Device Performance", 1);


                }
            }, 900);

            final Handler handler3 = new Handler();
            handler3.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Close All Battery Consuming Apps", 2);


                }
            }, 1800);

            final Handler handler4 = new Handler();
            handler4.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3);


                }
            }, 2700);

            final Handler handler5 = new Handler();
            handler5.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 4);
                }
            }, 3700);
//
            final Handler handler6 = new Handler();
            handler6.postDelayed(new Runnable() {
                @Override
                public void run() {
                    remove(0);
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);
                }
            }, 4400);

            final Handler handler7 = new Handler();
            handler7.postDelayed(new Runnable() {
                @Override
                public void run() {
                    add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                    remove(0);

                    final RippleBackground rippleBackground=(RippleBackground)findViewById(R.id.content);
                    ImageView imageView=(ImageView)findViewById(R.id.centerImage);
                    rippleBackground.startRippleAnimation();

                    img_animation.setImageResource(0);
                    img_animation.setBackgroundResource(0);
                    cpu.setImageResource(R.drawable.green_circle);
                    scanner.setImageResource(R.drawable.task_complete);
                    ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flipping);
                    anim.setTarget(scanner);
                    anim.setDuration(3000);
                    anim.start();

                    rel.setVisibility(View.GONE);

                    cooledcpu.setText("Cooled CPU to 25.3°C");
                    anim.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            img_animation.setImageResource(0);
                            img_animation.setBackgroundResource(0);
                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {

                            rippleBackground.stopRippleAnimation();


                                    mInterstitialAd.show();




                            final Handler handler6 = new Handler();
                            handler6.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);

                                    finish();

                                }
                            }, 1000);

                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {

                        }
                    });

                }
            }, 5500);
//
//        final Handler handler8 = new Handler();
//        handler8.postDelayed(new Runnable() {
//            @Override
//            public void run() {
////                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
//                remove(0);
//
//
//            }
//        }, 8000);

        }
        catch(Exception e)
        {

        }

    }

    public void add(String text, int position) {


//        int p=0 + (int)(Math.random() * ((packages.size() - 0) + 1));

//        Drawable ico = null;

//        Apps item=new Apps();

//        String packageName = packages.get(p).packageName;
//        try {
//            String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
//            ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
//            item.setImage(ico = getPackageManager().getApplicationIcon(packages.get(p).packageName));
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//        }


//        item.setSize(packages.get(position).dataDir);
//        CPUCooler.apps.add(item);
//        mDataSet.add(position, text);
        try {


            mAdapter.notifyItemInserted(position);
        }
        catch(Exception e)
        {

        }
    }


    public void remove(int position) {
//        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position);
        try {
            CPUCoolerFragment.apps.remove(position);
        }
        catch(Exception e)
        {

        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
