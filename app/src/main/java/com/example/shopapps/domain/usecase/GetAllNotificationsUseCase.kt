package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.Notification

class GetAllNotificationsUseCase(private val productRepository: ProductRepository) {
    fun execute(): LiveData<List<Notification>> = productRepository.getAllNotifications()
}
