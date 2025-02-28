package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.UserLocation

class AddUserLocationUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(userLocation: UserLocation) = productRepository.addUsersLocation(userLocation)
}
