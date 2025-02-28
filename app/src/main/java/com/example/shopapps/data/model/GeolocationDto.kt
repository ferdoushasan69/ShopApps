package com.example.shopapps.data.model


import com.example.shopapps.domain.model.user.GeoLocation
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeolocationDto(
    @SerialName("lat")
    val lat: String = "",
    @SerialName("long")
    val long: String = ""
){
    fun toDomainGeoLocation() = GeoLocation(
        lat = lat,
        long = long
    )
}