package com.frogobox.cleaner.utils;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.frogobox.cleaner.base.BaseActivity;
import com.frogobox.cleaner.view.fragment.BatterySaverFragment;
import com.frogobox.cleaner.view.fragment.CPUCoolerFragment;
import com.frogobox.cleaner.view.fragment.JunkCleanerFragment;
import com.frogobox.cleaner.view.fragment.ChargeBoosterFragment;

import java.util.ArrayList;

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

public class PagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> titles;

    public PagerAdapter(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<>();
        this.titles = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void addFragment(Fragment fragment, String title){
        fragments.add(fragment);
        titles.add(title);
    }

}
