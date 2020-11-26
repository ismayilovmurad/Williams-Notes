package com.martiandeveloper.williamsnotes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.martiandeveloper.williamsnotes.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setAds()
    }

    private fun setAds() {
        MobileAds.initialize(this)

        val bannerAdRequest = AdRequest.Builder().build()
        findViewById<AdView>(R.id.activity_main_mainAV).loadAd(bannerAdRequest)
    }

}
