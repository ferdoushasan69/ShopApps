package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.Order

class GetLatestOrderUseCase(private val productRepository: ProductRepository) {
    fun execute(): LiveData<Order> = productRepository.getLatestOrder()
}
