package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository

class LoginUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(email : String, password: String): String =
        productRepository.login(email = email, password = password)
}