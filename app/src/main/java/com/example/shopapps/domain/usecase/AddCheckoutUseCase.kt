package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.CheckOut

class AddCheckoutUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(checkout: CheckOut) = productRepository.addCheckout(checkout)
}
