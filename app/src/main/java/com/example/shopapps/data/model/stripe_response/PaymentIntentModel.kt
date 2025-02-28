package com.example.shopapps.data.model.stripe_response



data class PaymentIntentModel(
    val id: String,
    val clientSecret: String,

    )