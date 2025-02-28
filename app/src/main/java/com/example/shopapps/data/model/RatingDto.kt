package com.example.shopapps.data.model


import com.example.shopapps.domain.model.products.Rating
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RatingDto(
    @SerialName("count")
    val count: Int = 0,
    @SerialName("rate")
    val rate: Double = 0.0
){
    fun toDomainRating()= Rating(
        count = count,
        rate = rate
    )
}