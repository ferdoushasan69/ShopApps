package com.example.shopapps

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.shopapps.presentation.utils.DialogHelper
import com.stripe.android.PaymentConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseClass:Application() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        PaymentConfiguration.init(applicationContext,BuildConfig.PUBLIC_KEY)

        val notificationChannel = NotificationChannel(
            DialogHelper.NOTIFICATION_ID,"Product_notification", NotificationManager.IMPORTANCE_HIGH
        )

        notificationChannel.description = ""

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }
}