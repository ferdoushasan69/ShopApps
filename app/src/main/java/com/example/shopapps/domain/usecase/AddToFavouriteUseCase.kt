package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.Favorite

class AddToFavouriteUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(favorite: Favorite) = productRepository.addToFavourite(favorite)
}
