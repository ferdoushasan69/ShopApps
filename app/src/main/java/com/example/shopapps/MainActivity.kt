package com.example.shopapps


import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController

import com.example.shopapps.presentation.ui.navigation.Navigation
import com.example.shopapps.presentation.ui.theme.ShopAppsTheme
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var paymentSheet: PaymentSheet
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(
            Color.TRANSPARENT,
            Color.TRANSPARENT
        ))
        val pubKey = BuildConfig.PUBLIC_KEY
        val secrectKey = BuildConfig.SECRECT_KEY
        setContent {
            ShopAppsTheme {
               Navigation()
        Log.d(TAG, "onCreate: $pubKey secrect key is $secrectKey")
            }
        }
    }


}

