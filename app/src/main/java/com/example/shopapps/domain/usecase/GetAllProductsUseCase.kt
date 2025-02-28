package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.products.ProductItem
import javax.inject.Inject

class GetAllProductsUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun execute(limit: Int): List<ProductItem> = productRepository.getAllProducts(limit)
}
