package com.example.shopapps.domain.model.stripe_response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CustomerModel(
    @SerialName("id")
    val id: String ,

){
    fun toCustomerDomainModel()=CustomerModel(
        id = id
    )
}