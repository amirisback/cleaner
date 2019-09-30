package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.frogobox.cleaner.view.activity.MainActivity;

/**
 * Created by Frogobox Software Industries 3/14/2017.
 */

public class PowerConnectedBroadcastReceiver extends BroadcastReceiver{

    // bROAD CAST THAT lISTEN fOR charger Connected Events

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i=new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
