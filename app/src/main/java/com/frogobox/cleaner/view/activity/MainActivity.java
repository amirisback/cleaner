package com.frogobox.cleaner.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.utils.PagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends FragmentActivity {

    public static TextView name;
    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Thread.UncaughtExceptionHandler oldHandler =
                Thread.getDefaultUncaughtExceptionHandler();

        Thread.setDefaultUncaughtExceptionHandler(
                new Thread.UncaughtExceptionHandler() {
                    @Override
                    public void uncaughtException(
                            Thread paramThread,
                            Throwable paramThrowable
                    ) {
                        //Do your own error handling here

                        if (oldHandler != null)
                            oldHandler.uncaughtException(
                                    paramThread,
                                    paramThrowable
                            ); //Delegates to Android's error handling
                        else
                            System.exit(2); //Prevents the service/app from freezing
                    }
                });


//        setTheme(R.style.AppTheme1);
        name = findViewById(R.id.name);
        sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();


//        ImageView img_animation =  findViewById(R.id.backbar);
//
//        TranslateAnimation animation = new TranslateAnimation(0.0f, 1000.0f, 0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
//        animation.setDuration(10000);  // animation duration
//        animation.setRepeatCount(0);
//        animation.setInterpolator(new LinearInterpolator());// animation repeat count
////        animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
//        animation.setFillAfter(true);
//
//        img_animation.startAnimation(animation);


//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        ////// Create Tabs Layout.

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.phonebooster));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.battery_saver));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cooler));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.cleaner));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.setOffscreenPageLimit(4);
//        viewPager.setCurrentItem(4);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "0");
        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "0");
        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "0");
        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "0");
        editor.commit();
    }

    public class MyException extends Exception {
        // special exception code goes here
    }
}
