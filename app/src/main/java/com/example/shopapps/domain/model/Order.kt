package com.example.shopapps.domain.model

import com.example.shopapps.domain.model.Cart

data class Order(
    val id: Int = 0,

    val totalPrice: Double,

    val orderDate: Long=System.currentTimeMillis(),

    val items: List<Cart>
)
