package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.frogobox.cleaner.R;
import com.frogobox.cleaner.utils.Constant;
import com.frogobox.cleaner.view.fragment.PhoneBoosterFragment;

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

public final class AlarmBoosterBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        logicOnReceive(context, intent);
    }

    private void logicOnReceive(Context context, Intent intent) {
        SharedPreferences.Editor editor;
        SharedPreferences sharedpreferences;

        sharedpreferences = context.getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        editor.putString(Constant.Variable.SHARED_PREF_BOOSTER, "1");
        editor.commit();

        try {
            PhoneBoosterFragment.optimizebutton.setBackgroundResource(0);
            PhoneBoosterFragment.optimizebutton.setImageResource(0);
            PhoneBoosterFragment.optimizebutton.setImageResource(R.drawable.optimize);
        } catch (Exception e) {

        }

    }

}
