package com.example.shopapps.domain.repository

import com.example.shopapps.domain.model.stripe_response.CustomerModel
import com.example.shopapps.domain.model.stripe_response.PaymentIntentModel
import com.example.shopapps.presentation.ui.common.Resource
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun getCustomer():Flow<Resource<CustomerModel>>
    suspend fun getEphemeralKeys(customerId:String):Flow<Resource<CustomerModel>>

    suspend fun getPaymentIntent(
        customerId: String,
        amount: Int,
        currency: String
    ):Flow<Resource<PaymentIntentModel>>
}