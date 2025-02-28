package com.example.shopapps.domain.model.user

import com.example.shopapps.domain.model.user.Address
import com.example.shopapps.domain.model.user.Name

data class UserResponseDomain(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String,
    val v: Int
)
