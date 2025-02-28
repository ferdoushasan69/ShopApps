package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.Favorite

class GetAllFavouritesUseCase(private val productRepository: ProductRepository) {
    fun execute(): LiveData<List<Favorite>> = productRepository.getAllFavourites()
}
