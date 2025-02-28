package com.example.shopapps.domain.model.user

data class Address(
    val city: String,
    val geolocation: GeoLocation,
    val number: Int,
    val street: String,
    val zipcode: String
)
