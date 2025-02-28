package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.UserLocation

class GetUserLocationByIdUseCase(private val productRepository: ProductRepository) {
    fun execute(id: Int): LiveData<UserLocation> = productRepository.getUserLocationById(id)
}
