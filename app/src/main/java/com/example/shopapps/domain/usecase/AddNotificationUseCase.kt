package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.model.Notification
import com.example.shopapps.domain.repository.ProductRepository

class AddNotificationUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(notification: Notification) =
        productRepository.addNotification(notification)
}