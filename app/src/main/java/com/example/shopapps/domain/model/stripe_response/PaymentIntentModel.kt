package com.example.shopapps.domain.model.stripe_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentIntentModel(
    @SerialName("id")
    val id: String ,
    @SerialName("client_secret")
    val clientSecret: String,

    ){
    fun toPaymentIntentDomainModel()=PaymentIntentModel(
        id = id,
        clientSecret = clientSecret
    )
}