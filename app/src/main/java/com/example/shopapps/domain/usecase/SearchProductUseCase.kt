package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.model.products.ProductItem

class SearchProductUseCase(private val productRepository: ProductRepository) {
    suspend fun execute(query: String): List<ProductItem> = productRepository.searchProduct(query)
}
