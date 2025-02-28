package com.example.shopapps.data.di

import com.example.data.util.STRIPE_BASE_URL
import com.example.shopapps.data.remote.StripeApiService
import com.example.shopapps.data.repository.PaymentRepositoryImpl
import com.example.shopapps.domain.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object StripeModule {

    @Singleton
    @Provides
    fun provideStripRetrofit(): StripeApiService {
        return Retrofit.Builder()
            .baseUrl(STRIPE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StripeApiService::class.java)
    }

    @Singleton
    @Provides
    fun providePaymentRepository(stripeApiService: StripeApiService): PaymentRepository {
        return PaymentRepositoryImpl(stripeApiService)
    }
}