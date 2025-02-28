package com.example.shopapps.domain.model

data class Notification(
    val id: Int = 0,

    val notificationType: String,

    val firstProductName: String="",

    val quantityCheckout: Int=0,

    val firstProductImage: String="",

    val message: String,

    val messageDetail: String="",

    val date: Long = System.currentTimeMillis(),

    val isRead: Boolean=false
)
