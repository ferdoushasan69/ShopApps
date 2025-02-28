package com.example.shopapps.domain.model


data class Cart(
    var id: Int = 0,

    var productId: Int,

    var productName: String,

    var productPrice: String,

    var productImage: String,

    var productCategory: String,

    var productQuantity: Int,
)