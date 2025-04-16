package com.example.shopapps



import android.graphics.Color
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge


import com.example.shopapps.presentation.ui.navigation.Navigation
import com.example.shopapps.presentation.ui.theme.ShopAppsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.light(
            Color.TRANSPARENT,
            Color.TRANSPARENT
        ))

        setContent {
            ShopAppsTheme {
                Navigation()
            }
        }
    }


}

