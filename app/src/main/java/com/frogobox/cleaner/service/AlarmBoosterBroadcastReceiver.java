package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.frogobox.cleaner.view.fragment.PhoneBoosterFragment;

import com.frogobox.cleaner.myapplication.R;

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

public final class AlarmBoosterBroadcastReceiver extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    @Override
    public void onReceive(Context context, Intent intent) {

        sharedpreferences = context.getSharedPreferences("waseem", Context.MODE_PRIVATE);
//        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();



        /// when memory is orveloaded or increased


        editor = sharedpreferences.edit();
        editor.putString("booster", "1");
        editor.commit();

        try {
            PhoneBoosterFragment.optimizebutton.setBackgroundResource(0);
            PhoneBoosterFragment.optimizebutton.setImageResource(0);
            PhoneBoosterFragment.optimizebutton.setImageResource(R.drawable.optimize);
        }
        catch(Exception e)
        {

        }

    }
}
