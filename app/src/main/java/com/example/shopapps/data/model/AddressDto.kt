package com.example.shopapps.data.model


import com.example.shopapps.domain.model.user.Address
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddressDto(
    @SerialName("city")
    val city: String = "",
    @SerialName("geolocation")
    val geolocation: GeolocationDto = GeolocationDto(),
    @SerialName("number")
    val number: Int = 0,
    @SerialName("street")
    val street: String = "",
    @SerialName("zipcode")
    val zipcode: String = ""
){
    fun toDomainAddress()= Address(
        city = city,
        geolocation = geolocation.toDomainGeoLocation(),
        number = number,
        street = street,
        zipcode = zipcode
    )
}