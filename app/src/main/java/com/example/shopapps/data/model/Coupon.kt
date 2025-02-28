package com.example.shopapps.data.model


data class Coupon(
    val discountedPrice: String,
    val description: String,
    val expiredDate: String,
    val couponCode: String,
    val color1: androidx.compose.ui.graphics.Color,
    val color2: androidx.compose.ui.graphics.Color
)