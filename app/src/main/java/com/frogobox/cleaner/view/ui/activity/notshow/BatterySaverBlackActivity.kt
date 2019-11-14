package com.frogobox.cleaner.view.ui.activity.notshow

import android.Manifest
import android.app.Activity
import android.content.*
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.net.Uri
import android.os.BatteryManager
import android.os.Build
import android.os.Bundle
import android.provider.AlarmClock
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_1
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_2
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_3
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_4
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_WASEEMBEST
import kotlinx.android.synthetic.main.activity_powersaving_main.*
import java.util.*

/**
 * Created by Frogobox Software Industries 2/21/2017.
 */

class BatterySaverBlackActivity : BaseActivity() {

    // Apply Extrem Power Saving Mode by allowing very few Apps to run

    private var sharedpreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private var check = 0


    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0)
            batteryremaning.text = "Battery Remaning $level%"

            when {
                level <= 5 -> timeremaning.text = "3h 55m remaning"
                level in 6..10 -> timeremaning.text = "6h 0m remaning"
                level in 11..15 -> timeremaning.text = "8h 25m remaning"
                level in 16..25 -> timeremaning.text = "12h 55m remaning"
                level in 26..35 -> timeremaning.text = "19h 2m remaning"
                level in 36..50 -> timeremaning.text = "22h 0m remaning"
                level in 51..65 -> timeremaning.text = "28h 15m remaning"
                level in 66..75 -> timeremaning.text = "30h 55m remaning"
                level in 76..85 -> timeremaning.text = "38h 5m remaning"
                level in 86..100 -> timeremaning.text = "60h 0m remaning"
            }

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= 17) {
            setContentView(R.layout.activity_powersaving_maintextclock)
        } else {
            setContentView(R.layout.activity_powersaving_main)
        }

        sharedpreferences = getSharedPreferences(SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE)
        editor = sharedpreferences?.edit()
        changePicture()
        registerReceiver(this.mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        dots.setOnClickListener {
            if (check % 2 == 0) {
                disable.visibility = View.VISIBLE
                disable.setOnClickListener {
                    val i = Intent(this@BatterySaverBlackActivity, NormalModeBatterySaverActivity::class.java)
                    startActivity(i)

                    finish()
                }
                check++

            } else {
                disable.visibility = View.INVISIBLE
                check++
            }
        }


        alaram.setOnClickListener {
            editor?.putString(SHARED_PREF_BUTTON, "1")
            editor?.commit()

            when (Objects.requireNonNull(sharedpreferences?.getString(SHARED_PREF_BUTTON_1, "0"))) {
                "0" -> {
                    val i = Intent(this@BatterySaverBlackActivity, PickAppsActivity::class.java)
                    startActivity(i)
                }
                "1" -> playstore()
                "2" -> calculator()
                "3" -> alaram()
                "4" -> contacts()
                "5" -> map()
                "6" -> camera()
            }
        }

        calculator.setOnClickListener {
            editor?.putString(SHARED_PREF_BUTTON, "2")
            editor?.commit()

            when {
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "0" -> {
                    val i = Intent(this@BatterySaverBlackActivity, PickAppsActivity::class.java)
                    startActivity(i)
                }
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "1" -> playstore()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "2" -> calculator()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "3" -> alaram()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "4" -> contacts()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "5" -> map()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_2, "0") == "6" -> camera()
            }
        }

        phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            startActivity(intent)
        }

        internet.setOnClickListener {
            val urlString = "http://www.google.com"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null)
                startActivity(intent)
            }
        }

        settings.setOnClickListener { startActivityForResult(Intent(android.provider.Settings.ACTION_SETTINGS), 0) }
        messages.setOnClickListener {
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.type = "vnd.android-dir/mms-sms"
            startActivity(intent)
        }

        playstore.setOnClickListener {
            editor?.putString(SHARED_PREF_BUTTON, "3")
            editor?.commit()

            when {
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "0" -> {
                    val i = Intent(this@BatterySaverBlackActivity, PickAppsActivity::class.java)
                    startActivity(i)
                }
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "1" -> playstore()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "2" -> calculator()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "3" -> alaram()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "4" -> contacts()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "5" -> map()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_3, "0") == "6" -> camera()
            }
        }

        contacts.setOnClickListener {
            editor?.putString(SHARED_PREF_BUTTON, "4")
            editor?.commit()

            when {
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "0" -> {
                    val i = Intent(this@BatterySaverBlackActivity, PickAppsActivity::class.java)
                    startActivity(i)
                }
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "1" -> playstore()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "2" -> calculator()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "3" -> alaram()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "4" -> contacts()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "5" -> map()
                sharedpreferences?.getString(SHARED_PREF_BUTTON_4, "0") == "6" -> camera()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val uri = intent?.data
                val projection = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val cursor = contentResolver.query(uri!!, projection, null, null, null)
                cursor?.moveToFirst()
                val numberColumnIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val number = numberColumnIndex?.let { cursor.getString(it) }
                val nameColumnIndex = cursor?.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                val name = nameColumnIndex?.let { cursor.getString(it) }

                Log.d(TAG, "ZZZ number : $number , name : $name")

                val k = Intent(Intent.ACTION_DIAL)
                k.data = Uri.parse("tel:$number")
                startActivity(k)

            }
        }
    }

    private fun playstore() {
        //////play store///////////////////////////////////////
        val appPackageName = packageName // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/")))
        }

        ////////////////////////////////////////////////////////
    }

    fun contacts() {

        //////////////////contacts//////////////////////////////
        val uri = Uri.parse("content://contacts")
        val intent = Intent(Intent.ACTION_PICK, uri)
        intent.type = ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE
        startActivityForResult(intent, 1)
        ////////////////////////////////////////////////////////////
    }

    private fun calculator() {
        val intent = Intent()
        try {
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.component = ComponentName("com.sec.android.app.popupcalculator", "com.sec.android.app.popupcalculator.Calculator")
            startActivity(intent)
        } catch (e: Exception) {
            intent.action = Intent.ACTION_MAIN
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.component = ComponentName("com.android.calculator2", "com.android.calculator2.Calculator")
            startActivity(intent)
        }
    }

    private fun alaram() {
        val i = Intent(AlarmClock.ACTION_SET_ALARM)
        startActivity(i)
    }

    private fun map() {
        val uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?&daddr=%f,%f (%s)", 12f, 2f, "")
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity")
        try {
            startActivity(intent)
        } catch (ex: ActivityNotFoundException) {
            try {
                val unrestrictedIntent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(unrestrictedIntent)
            } catch (innerEx: ActivityNotFoundException) {
                Toast.makeText(this, "Please install a maps application", Toast.LENGTH_LONG).show()
            }

        }

    }

    private fun camera() {
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 1)
            }
        } else {
            val intent = Intent("android.media.action.IMAGE_CAPTURE")
            startActivityForResult(intent, 0)
        }
    }

    private fun changeIconImageView(pref: String, imageView: ImageView) {
        when {
            sharedpreferences?.getString(pref, "0") == "0" -> imageView.setImageResource(R.drawable.img_button_add)
            sharedpreferences?.getString(pref, "0") == "1" -> imageView.setImageResource(R.drawable.ic_apps_playstore)
            sharedpreferences?.getString(pref, "0") == "2" -> imageView.setImageResource(R.drawable.ic_apps_calc)
            sharedpreferences?.getString(pref, "0") == "3" -> imageView.setImageResource(R.drawable.ic_apps_clock)
            sharedpreferences?.getString(pref, "0") == "4" -> imageView.setImageResource(R.drawable.ic_apps_contacts)
            sharedpreferences?.getString(pref, "0") == "5" -> imageView.setImageResource(R.drawable.ic_apps_map)
            sharedpreferences?.getString(pref, "0") == "6" -> imageView.setImageResource(R.drawable.ic_apps_camera)
        }
    }

    private fun changePicture() {
        changeIconImageView(SHARED_PREF_BUTTON_1, alaram)
        changeIconImageView(SHARED_PREF_BUTTON_2, calculator)
        changeIconImageView(SHARED_PREF_BUTTON_3, playstore)
        changeIconImageView(SHARED_PREF_BUTTON_4, contacts)
    }


    override fun onResume() {
        super.onResume()
        changePicture()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent("android.media.action.IMAGE_CAPTURE")
                startActivityForResult(intent, 0)
            } else {
                Toast.makeText(this, "Allow Permission To Use Camera App.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mBatInfoReceiver)
    }
}