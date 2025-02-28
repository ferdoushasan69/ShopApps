package com.example.shopapps.data.model.response


import com.example.shopapps.data.model.AddressDto
import com.example.shopapps.data.model.NameDto
import com.example.shopapps.domain.model.user.UserResponseDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseItem(
    @SerialName("address")
    val address: AddressDto = AddressDto(),
    @SerialName("email")
    val email: String = "",
    @SerialName("id")
    val id: Int = 0,
    @SerialName("name")
    val name: NameDto = NameDto(),
    @SerialName("password")
    val password: String = "",
    @SerialName("phone")
    val phone: String = "",
    @SerialName("username")
    val username: String = "",
    @SerialName("__v")
    val v: Int = 0
){
    fun toDomainUserItem()= UserResponseDomain(
        address = address.toDomainAddress(),
        email = email,
        id = id,
        name = name.toDomainName(),
        password = password,
        phone = phone,
        username = username,
        v = v
    )
}