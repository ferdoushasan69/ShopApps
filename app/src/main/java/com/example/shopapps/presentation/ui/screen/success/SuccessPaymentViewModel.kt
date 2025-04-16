package com.example.shopapps.presentation.ui.screen.success

import androidx.lifecycle.ViewModel
import com.example.shopapps.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SuccessPaymentViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    val latestCheckout = repository.getLatestCheckout()
}