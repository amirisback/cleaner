package com.frogobox.cleaner.view.ui.activity


import android.content.Context
import android.os.Bundle
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_1
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_2
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_3
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_BUTTON_4
import com.frogobox.cleaner.utils.Constant.Variable.SHARED_PREF_WASEEMBEST
import kotlinx.android.synthetic.main.activity_pick_apps.*

/**
 * Created by Frogobox Software Industries 2/26/2017.
 */

class PickAppsActivity : BaseActivity() {

    // Choose App to Add it in Usable APP LIST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_apps)

        val sharedpreferences = getSharedPreferences(SHARED_PREF_WASEEMBEST, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()

        addcontacts.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "4" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "4" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "4" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "4")) {
                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "4")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "4")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "4")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "4")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

        addplaystore.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "1" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "1" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "1" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "1")) {

                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "1")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "1")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "1")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "1")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

        addcalculator.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "2" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "2" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "2" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "2")) {

                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "2")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "2")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "2")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "2")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

        addclock.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "3" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "3" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "3" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "3")) {

                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "3")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "3")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "3")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "3")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

        addmap.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "5" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "5" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "5" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "5")) {

                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "5")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "5")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "5")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "5")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

        addcamera.setOnClickListener {
            if (!(sharedpreferences.getString(SHARED_PREF_BUTTON_1, "l") == "6" || sharedpreferences.getString(SHARED_PREF_BUTTON_2, "l") == "6" || sharedpreferences.getString(SHARED_PREF_BUTTON_3, "l") == "6" || sharedpreferences.getString(SHARED_PREF_BUTTON_4, "l") == "6")) {

                if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "1") {
                    editor.putString(SHARED_PREF_BUTTON_1, "6")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "2") {
                    editor.putString(SHARED_PREF_BUTTON_2, "6")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "3") {
                    editor.putString(SHARED_PREF_BUTTON_3, "6")
                    editor.commit()
                } else if (sharedpreferences.getString(SHARED_PREF_BUTTON, "1") == "4") {
                    editor.putString(SHARED_PREF_BUTTON_4, "6")
                    editor.commit()
                }

                finish()
            } else {
                showCustomToast("This App Is Already Added")
            }
        }

    }
}
