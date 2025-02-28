package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.Order

class AddOrderUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(order: Order) = productRepository.addOrder(order)
}
