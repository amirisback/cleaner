package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.frogobox.cleaner.utils.Constant;

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

public final class AlarmJunkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences.Editor editor;
        SharedPreferences sharedpreferences;
        sharedpreferences = context.getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEM, Context.MODE_PRIVATE);

        editor = sharedpreferences.edit();
        editor.putString(Constant.Variable.SHARED_PREF_JUNK, "1");
        editor.commit();

    }
}
