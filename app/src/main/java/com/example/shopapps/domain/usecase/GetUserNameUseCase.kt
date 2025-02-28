package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetUserNameUseCase(private val productRepository: ProductRepository) {
    suspend fun execute():Flow<String> = productRepository.getUserName()
}