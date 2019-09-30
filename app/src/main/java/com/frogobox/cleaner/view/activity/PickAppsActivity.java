package com.frogobox.cleaner.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.frogobox.cleaner.myapplication.R;
import com.frogobox.cleaner.utils.Constant;

/**
 * Created by Frogobox Software Industries 2/26/2017.
 */

public class PickAppsActivity extends Activity {

    private SharedPreferences sharedpreferences;
    private SharedPreferences.Editor editor;

    /// Choose App to Add it in Usable APP LIST

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_apps);

        sharedpreferences = getSharedPreferences(Constant.Variable.SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        ImageView addcontact = findViewById(R.id.addcontacts);
        ImageView addcamera = findViewById(R.id.addcamera);
        ImageView addplaystore = findViewById(R.id.addplaystore);
        ImageView addcalculator = findViewById(R.id.addcalculator);
        ImageView addclock = findViewById(R.id.addclock);
        ImageView addmap = findViewById(R.id.addmap);

        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("4") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("4") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("4") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("4"))) {
                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "4");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "4");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "4");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "4");
                        editor.commit();
                    }

                    finish();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                   Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addplaystore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("1") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("1") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("1") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("1"))) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "1");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "1");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "1");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "1");
                        editor.commit();
                    }

                    finish();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addcalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("2") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("2") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("2") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("2"))) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "2");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "2");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "2");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "2");
                        editor.commit();
                    }

                    finish();
                } else {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addclock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("3") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("3") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("3") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("3"))) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "3");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "3");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "3");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "3");
                        editor.commit();
                    }

                    finish();
                } else {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("5") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("5") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("5") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("5"))) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "5");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "5");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "5");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "5");
                        editor.commit();
                    }

                    finish();
                } else {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();

//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();
                }

            }
        });

        addcamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_1, "l").equals("6") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_2, "l").equals("6") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_3, "l").equals("6") || sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON_4, "l").equals("6"))) {

                    if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("1")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_1, "6");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("2")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_2, "6");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("3")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_3, "6");
                        editor.commit();
                    } else if (sharedpreferences.getString(Constant.Variable.SHARED_PREF_BUTTON, "1").equals("4")) {
                        editor.putString(Constant.Variable.SHARED_PREF_BUTTON_4, "6");
                        editor.commit();
                    }

                    finish();
                } else {
//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.toast_apps, null);

                    ImageView image = layout.findViewById(R.id.image);

                    TextView text = layout.findViewById(R.id.textView1);
                    text.setText("This App Is Already Added");

                    Toast toast = new Toast(PickAppsActivity.this);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 70);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }

            }
        });


    }
}
