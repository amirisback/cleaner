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

/**
 * Created by Frogobox Software Industries 2/26/2017.
 */

public class PickAppsActivity extends Activity{
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    ImageView addcontact,addplaystore,addcalculator,addcamera,addclock,addmap;


    /// Choose App to Add it in Usable APP LIST

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pick_apps);

        sharedpreferences = getSharedPreferences("waseembest", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();

        addcontact=(ImageView) findViewById(R.id.addcontacts);
        addcamera=(ImageView) findViewById(R.id.addcamera);
        addplaystore=(ImageView) findViewById(R.id.addplaystore);
        addcalculator=(ImageView) findViewById(R.id.addcalculator);
        addclock=(ImageView) findViewById(R.id.addclock);
        addmap=(ImageView) findViewById(R.id.addmap);


        addcontact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(!(sharedpreferences.getString("button1","l").equals("4")||sharedpreferences.getString("button2","l").equals("4")||sharedpreferences.getString("button3","l").equals("4")||sharedpreferences.getString("button4","l").equals("4"))) {
                   if (sharedpreferences.getString("button", "1").equals("1")) {
                       editor.putString("button1", "4");
                       editor.commit();
                   } else if (sharedpreferences.getString("button", "1").equals("2")) {
                       editor.putString("button2", "4");
                       editor.commit();
                   } else if (sharedpreferences.getString("button", "1").equals("3")) {
                       editor.putString("button3", "4");
                       editor.commit();
                   } else if (sharedpreferences.getString("button", "1").equals("4")) {
                       editor.putString("button4", "4");
                       editor.commit();
                   }

                   finish();
               }
                else
               {
                   LayoutInflater inflater = getLayoutInflater();
                   View layout = inflater.inflate(R.layout.my_toast, null);

                   ImageView image = (ImageView) layout.findViewById(R.id.image);

                   TextView text = (TextView) layout.findViewById(R.id.textView1);
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

                if(!(sharedpreferences.getString("button1","l").equals("1")||sharedpreferences.getString("button2","l").equals("1")||sharedpreferences.getString("button3","l").equals("1")||sharedpreferences.getString("button4","l").equals("1"))) {

                    if (sharedpreferences.getString("button", "1").equals("1")) {
                        editor.putString("button1", "1");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("2")) {
                        editor.putString("button2", "1");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("3")) {
                        editor.putString("button3", "1");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("4")) {
                        editor.putString("button4", "1");
                        editor.commit();
                    }

                    finish();
                }
                else
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
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

                if(!(sharedpreferences.getString("button1","l").equals("2")||sharedpreferences.getString("button2","l").equals("2")||sharedpreferences.getString("button3","l").equals("2")||sharedpreferences.getString("button4","l").equals("2"))) {

                    if (sharedpreferences.getString("button", "1").equals("1")) {
                        editor.putString("button1", "2");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("2")) {
                        editor.putString("button2", "2");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("3")) {
                        editor.putString("button3", "2");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("4")) {
                        editor.putString("button4", "2");
                        editor.commit();
                    }

                    finish();
                }
                else
                {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
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

                if(!(sharedpreferences.getString("button1","l").equals("3")||sharedpreferences.getString("button2","l").equals("3")||sharedpreferences.getString("button3","l").equals("3")||sharedpreferences.getString("button4","l").equals("3"))) {

                    if (sharedpreferences.getString("button", "1").equals("1")) {
                        editor.putString("button1", "3");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("2")) {
                        editor.putString("button2", "3");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("3")) {
                        editor.putString("button3", "3");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("4")) {
                        editor.putString("button4", "3");
                        editor.commit();
                    }

                    finish();
                }
                else
                {
                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
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


                if(!(sharedpreferences.getString("button1","l").equals("5")||sharedpreferences.getString("button2","l").equals("5")||sharedpreferences.getString("button3","l").equals("5")||sharedpreferences.getString("button4","l").equals("5"))) {

                    if (sharedpreferences.getString("button", "1").equals("1")) {
                        editor.putString("button1", "5");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("2")) {
                        editor.putString("button2", "5");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("3")) {
                        editor.putString("button3", "5");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("4")) {
                        editor.putString("button4", "5");
                        editor.commit();
                    }

                    finish();
                }
                else
                {

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
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

                if(!(sharedpreferences.getString("button1","l").equals("6")||sharedpreferences.getString("button2","l").equals("6")||sharedpreferences.getString("button3","l").equals("6")||sharedpreferences.getString("button4","l").equals("6"))) {

                    if (sharedpreferences.getString("button", "1").equals("1")) {
                        editor.putString("button1", "6");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("2")) {
                        editor.putString("button2", "6");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("3")) {
                        editor.putString("button3", "6");
                        editor.commit();
                    } else if (sharedpreferences.getString("button", "1").equals("4")) {
                        editor.putString("button4", "6");
                        editor.commit();
                    }

                    finish();
                }
                else
                {
//                    Toast.makeText(Pick_Apps.this, "Choose Another App This App Is Already Added", Toast.LENGTH_SHORT).show();

                    LayoutInflater inflater = getLayoutInflater();
                    View layout = inflater.inflate(R.layout.my_toast, null);

                    ImageView image = (ImageView) layout.findViewById(R.id.image);

                    TextView text = (TextView) layout.findViewById(R.id.textView1);
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
