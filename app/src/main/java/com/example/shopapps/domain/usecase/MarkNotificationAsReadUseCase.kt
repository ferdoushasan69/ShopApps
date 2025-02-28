package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository

class MarkNotificationAsReadUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(notificationId: Int) = productRepository.markAsRead(notificationId)
}
