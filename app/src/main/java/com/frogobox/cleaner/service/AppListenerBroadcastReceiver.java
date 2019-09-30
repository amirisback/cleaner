package com.frogobox.cleaner.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frogobox.cleaner.myapplication.R;
import com.frogobox.cleaner.utils.Constant;

/**
 * Created by Frogobox Software Industries 3/3/2017.
 */

public class AppListenerBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent arg1) {
        logicOnReceive(context, arg1);
    }

    private void logicOnReceive(Context context, Intent arg1) {
        Uri data = arg1.getData();
        String installedPackageName = data.getEncodedSchemeSpecificPart();

        if (!(installedPackageName.equals(Constant.Variable.PACKAGE_NAME))) {
//            Toast.makeText(context, installedPackageName + "", Toast.LENGTH_SHORT).show();

            final String packageName = installedPackageName;
            PackageManager packageManager = context.getApplicationContext().getPackageManager();
            try {
                String appName = (String) packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                LayoutInflater inflater = LayoutInflater.from(context);
                View layout = inflater.inflate(R.layout.my_toast, null);
                ImageView image = layout.findViewById(R.id.image);
                TextView text = layout.findViewById(R.id.textView1);
                String notif = appName + " Is Optimized by" + context.getString(R.string.app_name);
                text.setText(notif);

                Toast toast = new Toast(context);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 120);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(layout);
                toast.show();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
