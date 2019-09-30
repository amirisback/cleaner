package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

/**
 * Created by Frogobox Software Industries 3/2/2017.
 */

public final class AlarmJunkBroadcastReceiver extends BroadcastReceiver {
    SharedPreferences.Editor editor;
    SharedPreferences sharedpreferences;
    @Override
    public void onReceive(Context context, Intent intent) {

        sharedpreferences = context.getSharedPreferences("waseem", Context.MODE_PRIVATE);
//        Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();



        /// notify user to clean junk files that junk has been appeared


        editor = sharedpreferences.edit();
        editor.putString("junk", "1");
        editor.commit();

//        try {
//            JunkCleaner.mainbutton.setBackgroundResource(0);
//            JunkCleaner.mainbutton.setImageResource(0);
//            JunkCleaner.mainbutton.setImageResource(R.drawable.clean);
//        }
//        catch(Exception e)
//        {
//
//        }

    }
}
