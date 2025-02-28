package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.repository.ProductRepository

class GetProductByCategory(private val productRepository: ProductRepository) {
    suspend fun execute(category: String, limit: Int): List<ProductItem> =
        productRepository.getProductByCategory(category = category, limit = limit)
}