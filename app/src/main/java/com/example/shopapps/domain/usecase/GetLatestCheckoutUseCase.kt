package com.example.shopapps.domain.usecase

import com.example.shopapps.domain.repository.ProductRepository
import androidx.lifecycle.LiveData
import com.example.shopapps.domain.model.CheckOut

class GetLatestCheckoutUseCase(private val productRepository: ProductRepository) {
    fun execute(): LiveData<CheckOut> = productRepository.getLatestCheckout()
}
