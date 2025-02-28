package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.Cart

class GetAllCartItemsUseCase(private val productRepository: ProductRepository) {
    fun execute(): LiveData<List<Cart>> = productRepository.getAllCartItems()
}
