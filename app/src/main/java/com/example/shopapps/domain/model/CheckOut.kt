package com.example.shopapps.domain.model

import com.example.shopapps.data.local.entity.getCurrentFormattedDate
import com.example.shopapps.data.local.entity.getCurrentFormattedTime


data class CheckOut(
    val id: Int=0,
    val receiverName: String,

    val receiverAddress: String,

    val orderItems: List<Cart>,

    val shippingMethod: String,

    val coupon: String,

    val formattedCheckoutDate: String= getCurrentFormattedDate(),

    val formattedCheckoutTime: String= getCurrentFormattedTime(),

    val checkoutDate: Long=System.currentTimeMillis(),

    val shippingCost: Double,

    val shippingDescription: String,

    val paymentMethod: String,

    val totalPrice: Double,
)
