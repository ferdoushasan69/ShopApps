package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.model.LoginResponseDomain
import com.example.shopapps.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetSessionUseCase(private val productRepository: ProductRepository) {
    suspend fun execute() : Flow<LoginResponseDomain> = productRepository.getSession()
}