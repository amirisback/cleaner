package com.frogobox.cleaner.view.activity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.base.BaseActivity;
import com.frogobox.cleaner.model.Apps;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.adapter.JunkAppsViewAdapter;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.skyfishjy.library.RippleBackground;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

/**
 * Created by Frogobox Software Industries 2/24/2017.
 */

public class ScanningJunkActivity extends BaseActivity {

    private AVLoadingIndicatorView avi1, avi2, avi3, avi4, avi5, avi6;
    private ImageView front, back;
    private Runnable myRunnable2;
    private Handler myHandler2;
    private Handler myHandler;
    private Runnable myRunnable;
    private int check = 0;
    private TextView files;
    private List<ApplicationInfo> packages;
    private int prog = 0;
    private Timer T2, T3;
    private JunkAppsViewAdapter mAdapter;
    private List<Apps> apps;
    private PackageManager pm;
    private ImageView mImgCheck;
    private TextView scanning;
    private InterstitialAd mInterstitialAd;


    //// Scanning for any junk file existance inorder to bg_button_clean it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle junk = getIntent().getExtras();
        setContentView(R.layout.activity_scanning_junk);
        avi1 = findViewById(R.id.scan1);
        avi2 = findViewById(R.id.scan2);
        avi3 = findViewById(R.id.scan3);
        avi4 = findViewById(R.id.scan4);
        avi5 = findViewById(R.id.scan5);
        avi6 = findViewById(R.id.scan6);
        files = findViewById(R.id.files);
        back = findViewById(R.id.back);
        scanning = findViewById(R.id.scanning);
        apps = new ArrayList<>();


        mInterstitialAd = new InterstitialAd(getApplicationContext());
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.admob_interstitial));
        AdRequest adRequestInter = new AdRequest.Builder().build();
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

            }
        });
        mInterstitialAd.loadAd(adRequestInter);


        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(1500);
        rotate.setRepeatCount(4);
        rotate.setInterpolator(new LinearInterpolator());

        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                T2.cancel();
                T2.purge();
                avi1.hide();
                avi2.hide();
                avi3.hide();
                avi4.hide();
                avi5.hide();
                avi6.hide();


                files.setText("");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                check++;
                startAnim(check);
            }
        });


        front = findViewById(R.id.front);

        front.startAnimation(rotate);

        pm = getPackageManager();

        packages = pm.getInstalledApplications(0);

        ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);


        T2 = new Timer();
        T2.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (prog < packages.size()) {
                            files.setText("" + packages.get(prog).sourceDir);
                            prog++;
                        } else {
                            T2.cancel();
                            T2.purge();
                        }

                    }
                });
            }
        }, 80, 80);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        recyclerView.setItemAnimator(new SlideInLeftAnimator());
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
//                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
//                recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));

//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {

//        recyclerView.getItemAnimator().setAddDuration(200);

        mAdapter = new JunkAppsViewAdapter(apps);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        recyclerView.computeHorizontalScrollExtent();
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));


        //Do something after 100ms


        final Handler handler1 = new Handler();
        handler1.postDelayed(new Runnable() {
            @Override
            public void run() {
                add("Limit Brightness Upto 80%", 0);


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
                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 3);
            }
        }, 4000);

        final Handler handler5 = new Handler();
        handler5.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 4);

                remove(0);


            }
        }, 5000);

        final Handler handler6 = new Handler();
        handler6.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 5);
                remove(0);


            }
        }, 6000);

        final Handler handler7 = new Handler();
        handler7.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                remove(0);


            }
        }, 7000);

        final Handler handler8 = new Handler();
        handler8.postDelayed(new Runnable() {
            @Override
            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
                remove(0);

                final RippleBackground rippleBackground = (RippleBackground) findViewById(R.id.content);
                ImageView imageView = findViewById(R.id.centerImage);
                rippleBackground.startRippleAnimation();
//                front.setImageResource(0);
//                imageView.setImageResource(0);
//                front.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.task_complete));
//                imageView.setImageDrawable(ContextCompat.getDrawable(Sacnning_Junk.this, R.drawable.circle_line_green));
                front.setImageResource(R.drawable.img_scan_task_complete);
                back.setImageResource(R.drawable.circle_line_green);

                ProgressBar progressBar = (ProgressBar) findViewById(R.id.spin_kit);
                ThreeBounce doubleBounce = new ThreeBounce();
                progressBar.setIndeterminateDrawable(doubleBounce);
                progressBar.setVisibility(View.GONE);

                scanning.setPadding(20, 0, 0, 0);


                if (Build.VERSION.SDK_INT < 23) {

                    scanning.setTextAppearance(getApplicationContext(), android.R.style.TextAppearance_Medium);
                    scanning.setText(junk.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared");

                } else {

                    scanning.setTextAppearance(android.R.style.TextAppearance_Medium);
                    scanning.setText(junk.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB of Junk Files Are Cleared");
                }


                ObjectAnimator anim = (ObjectAnimator) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.flipping);
                anim.setTarget(front);
                anim.setDuration(3000);
                anim.start();

                anim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                        scanning.setText("Cleared " + junk.getString(Constant.Variable.SHARED_PREF_JUNK) + " MB");
                        scanning.setTextColor(getColorRes(R.color.colorTextWhite));
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        mInterstitialAd.show();

                        rippleBackground.stopRippleAnimation();

                        final Handler handler7 = new Handler();
                        handler7.postDelayed(new Runnable() {
                            @Override
                            public void run() {
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
//                add("Closes System Services like Bluetooth,Screen Rotation,Sync etc.", 6);
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

                files.setText("");


            }
        }, 8000);

//            }
//        }, 1000);


    }


    void startAnim(int i1) {
        if (i1 == 1) {
            avi1.show();
            avi3.show();
            avi5.show();

            avi2.hide();
            avi4.hide();
            avi6.hide();
        } else if (i1 == 2) {
            avi2.show();
            avi4.show();
            avi6.show();

            avi1.hide();
            avi3.hide();
            avi5.hide();
        } else if (i1 == 3) {
            avi2.show();
            avi4.show();
            avi6.show();

            avi1.show();
            avi3.show();
            avi5.show();
        } else if (i1 == 4) {
            avi2.show();
            avi3.show();
            avi5.show();

            avi1.show();
            avi2.show();
            avi6.show();
        }
        // or avi.smoothToShow();
    }

    void stopAnim() {
        avi1.hide();
        avi3.hide();
        avi5.hide();

        avi2.show();
        avi4.show();
        avi6.show();
        // or avi.smoothToHide();
    }

    public void add(String text, int position) {


        int p = 0 + (int) (Math.random() * ((packages.size() - 1 - 0) + 1));


        Drawable ico = null;

        Apps item = new Apps();

        String packageName = packages.get(p).packageName;
        try {
            String pName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
            ApplicationInfo a = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            item.setImage(ico = getPackageManager().getApplicationIcon(packages.get(p).packageName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        item.setSize(packages.get(p).dataDir);
        apps.add(item);
//        mDataSet.add(position, text);
        mAdapter.notifyItemInserted(position);
    }

    public void remove(int position) {
//        mDataSet.add(position, text);
        mAdapter.notifyItemRemoved(position);
        apps.remove(position);
    }

    public class SimpleDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable mDivider;

        public SimpleDividerItemDecoration(Context context) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mDivider = context.getResources().getDrawable(R.drawable.line_divide, context.getTheme());
            } else {
                mDivider = context.getResources().getDrawable(R.drawable.line_divide);

            }
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDivider.getIntrinsicHeight();

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }
}
