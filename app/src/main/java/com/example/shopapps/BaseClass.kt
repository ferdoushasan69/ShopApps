package com.example.shopapps

import android.app.Application
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseClass:Application() {
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(applicationContext,BuildConfig.PUBLIC_KEY)
    }
}