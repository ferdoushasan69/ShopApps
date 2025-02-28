package com.example.shopapps.data.repository

import android.content.ContentValues.TAG
import android.util.Log
import com.example.shopapps.data.remote.StripeApiService
import com.example.shopapps.domain.model.stripe_response.CustomerModel
import com.example.shopapps.domain.model.stripe_response.PaymentIntentModel
import com.example.shopapps.domain.repository.PaymentRepository
import com.example.shopapps.presentation.ui.common.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val stripeApiService: StripeApiService
): PaymentRepository {

    override suspend fun getCustomer(): Flow<Resource<CustomerModel>> = flow {
        emit(Resource.Loading)
        val response = stripeApiService.getCustomer()

        if (response.isSuccessful && response.body() != null) {
            val customerModel = response.body()!!.toCustomerDomainModel()
            emit(Resource.Success(customerModel))
        } else {
            emit(Resource.Error("Failed to get customer"))
        }
    }
    override suspend fun getEphemeralKeys(customerId: String): Flow<Resource<CustomerModel>> = flow {
        emit(Resource.Loading)
        val response = stripeApiService.getEphemeralKeys(customerId)
        if (response.isSuccessful && response.body() != null) {
            val customerModel = response.body()!!.toCustomerDomainModel()
            emit(Resource.Success(customerModel))
        } else {
            emit(Resource.Error("Failed to get customer"))
        }
    }

    override suspend fun getPaymentIntent(
        customerId: String,
        amount: Int,
        currency: String
    ): Flow<Resource<PaymentIntentModel>> = flow {
        emit(Resource.Loading)
        val response = stripeApiService.getPaymentIntent(customerId, amount, currency)

        if (response.isSuccessful && response.body() != null) {
            val paymentIntentModel = response.body()!!.toPaymentIntentDomainModel()
            emit(Resource.Success(paymentIntentModel))
        } else {
            emit(Resource.Error("Failed to get payment intent"))
        }
    }
}
