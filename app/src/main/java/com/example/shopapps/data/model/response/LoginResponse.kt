package com.example.shopapps.data.model.response

import com.example.shopapps.domain.model.LoginResponseDomain
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    @SerialName("token")
    val token :String = ""
){
    fun toDomainLoginResponse()= LoginResponseDomain(
        token = token
    )
}
