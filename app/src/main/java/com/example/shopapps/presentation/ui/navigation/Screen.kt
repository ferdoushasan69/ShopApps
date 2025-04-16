package com.example.shopapps.presentation.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Welcome

@Serializable
data object Login

@Serializable
data object Register

@Serializable
data object Home

@Serializable
data object Product
@Serializable
data object Coupon

@Serializable
data object Favorite

@Serializable
data object Profile

@Serializable
data object Notification

@Serializable
data object MyCart

@Serializable
data class ProductDetails(val productId : Int)

@Serializable
data object Category



@Serializable
data object CheckOut

@Serializable
data object Success