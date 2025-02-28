package com.example.shopapps.data.model


import com.example.shopapps.domain.model.user.Name
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NameDto(
    @SerialName("firstname")
    val firstname: String = "",
    @SerialName("lastname")
    val lastname: String = ""
){
    fun toDomainName()= Name(
        firstname = firstname,
        lastname = lastname
    )
}