package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.Cart

class AddToCartUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(cart: Cart) = productRepository.addToCart(cart)
}
