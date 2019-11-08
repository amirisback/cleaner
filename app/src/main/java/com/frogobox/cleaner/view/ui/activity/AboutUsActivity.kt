package com.frogobox.cleaner.view.ui.activity

import android.os.Bundle
import com.frogobox.cleaner.R
import com.frogobox.cleaner.base.BaseActivity

class AboutUsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)
        setupDetailActivity("")
        setupShowAdsInterstitial()
    }

}
