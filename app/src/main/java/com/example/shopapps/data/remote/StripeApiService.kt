package com.example.shopapps.data.remote


import com.example.shopapps.domain.model.stripe_response.CustomerModel
import com.example.shopapps.domain.model.stripe_response.PaymentIntentModel
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

const val secret = "sk_test_51Qvd1SRxyAXn0P30dQ2aLxjDTFfz9Rzr9lUsWdaHsPrqBiwhIja5RDgH0tAYmzxfSDAKgGSdz5iiAv6fYzHT9ZQc00PUwOkmu1"
interface StripeApiService {

    @Headers("Authorization: Bearer $secret")
    @POST("v1/customers")
    suspend fun getCustomer() : Response<CustomerModel>

    @Headers("Authorization: Bearer $secret",
        "Stripe-Version: 2023-10-16")
    @POST("v1/ephemeral_keys")
    suspend fun getEphemeralKeys(
         @Query("customer") customer : String
    ) : Response<CustomerModel>

    @Headers("Authorization: Bearer $secret")
    @POST("v1/payment_intents")
    suspend fun getPaymentIntent(
        @Query("customer") customer : String,
        @Query("amount") amount : Int=1099,
        @Query("currency") currency : String = "eur",
        @Query("automatic_payment_methods[enabled]") automaticPaymentMethods : Boolean = true

    ) : Response<PaymentIntentModel>
}