package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository

class RegisterUseCase(private val productRepository: ProductRepository) {
    suspend fun execute( email: String, password: String): String  = productRepository.register(email = email, password = password)
}