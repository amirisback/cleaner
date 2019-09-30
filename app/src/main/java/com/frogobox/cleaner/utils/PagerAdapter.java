package com.frogobox.cleaner.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.frogobox.cleaner.view.fragment.BatterySaverFragment;
import com.frogobox.cleaner.view.fragment.CPUCoolerFragment;
import com.frogobox.cleaner.view.fragment.JunkCleanerFragment;
import com.frogobox.cleaner.view.fragment.PhoneBoosterFragment;

/**
 * Created by Frogobox Software Industries 2/12/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PhoneBoosterFragment tab1 = new PhoneBoosterFragment();
                return tab1;
            case 1:
                BatterySaverFragment tab2 = new BatterySaverFragment();
                return tab2;
            case 2:
                CPUCoolerFragment tab3 = new CPUCoolerFragment();
                return tab3;
            case 3:
                JunkCleanerFragment tab4 = new JunkCleanerFragment();
                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
