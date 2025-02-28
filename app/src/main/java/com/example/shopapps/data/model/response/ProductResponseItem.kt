package com.example.shopapps.data.model.response


import com.example.shopapps.data.model.RatingDto
import com.example.shopapps.domain.model.products.ProductItem
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponseItem(
    @SerialName("category")
    val category: String = "",
    @SerialName("description")
    val description: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("image")
    val image: String = "",
    @SerialName("price")
    val price: Double = 0.0,
    @SerialName("rating")
    val rating: RatingDto = RatingDto(),
    @SerialName("title")
    val title: String = ""
){
    fun toDomainProductItem()= ProductItem(
        category = category,
        description = description,
        id = id,
        image =image,
        price = price,
        rating = rating.toDomainRating(),
        title = title
    )
}