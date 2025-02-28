package com.example.shopapps.data.model.stripe_response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssociatedObject(
    @SerialName("id")
    val id: String = "",
    @SerialName("type")
    val type: String = ""
)