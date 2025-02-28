package com.example.shopapps.di

import android.content.Context
import android.location.Geocoder
import com.example.shopapps.data.local.dao.CartDao
import com.example.shopapps.data.local.dao.CheckoutDao
import com.example.shopapps.data.local.dao.FavoriteDao
import com.example.shopapps.data.local.dao.NotificationDao
import com.example.shopapps.data.local.dao.OrderDao
import com.example.shopapps.data.local.dao.UserLocationDao
import com.example.shopapps.data.remote.ApiService
import com.example.shopapps.data.repository.ProductRepositoryImpl
import com.example.shopapps.data.utils.FirebaseManager
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.usecase.AddCheckoutUseCase
import com.example.shopapps.domain.usecase.AddNotificationUseCase
import com.example.shopapps.domain.usecase.AddOrderUseCase
import com.example.shopapps.domain.usecase.AddToCartUseCase
import com.example.shopapps.domain.usecase.AddToFavouriteUseCase
import com.example.shopapps.domain.usecase.AddUserLocationUseCase
import com.example.shopapps.domain.usecase.GetAllCartItemsUseCase
import com.example.shopapps.domain.usecase.GetAllFavouritesUseCase
import com.example.shopapps.domain.usecase.GetAllNotificationsUseCase
import com.example.shopapps.domain.usecase.GetLatestCheckoutUseCase
import com.example.shopapps.domain.usecase.GetLatestOrderUseCase
import com.example.shopapps.domain.usecase.GetProductByCategory
import com.example.shopapps.domain.usecase.GetSessionUseCase
import com.example.shopapps.domain.usecase.GetUserLocationByIdUseCase
import com.example.shopapps.domain.usecase.GetUserNameUseCase
import com.example.shopapps.domain.usecase.LoginUseCase
import com.example.shopapps.domain.usecase.MarkNotificationAsReadUseCase
import com.example.shopapps.domain.usecase.RegisterUseCase
import com.example.shopapps.domain.usecase.SearchProductUseCase
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun provideProductRepository(
        firebaseManager: FirebaseManager,
        apiService: ApiService,
        cartDao: CartDao,
        checkoutDao: CheckoutDao,
        favoriteDao: FavoriteDao,
        notificationDao: NotificationDao,
        orderDao: OrderDao,
        userLocationDao: UserLocationDao,
        fusedLocationProviderClient: FusedLocationProviderClient,
        geocoder: Geocoder
    ): ProductRepository {
        return ProductRepositoryImpl(
            firebaseManager = firebaseManager,
            apiService = apiService,
            cartDao = cartDao,
            checkoutDao = checkoutDao,
            favoriteDao = favoriteDao,
            notificationDao = notificationDao,
            orderDao = orderDao,
            userLocationDao = userLocationDao,
            fusedLocationProviderClient = fusedLocationProviderClient,
            geocoder = geocoder
        )
    }
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    // Example: If you're injecting ProductRepository and it needs FusedLocationProviderClient

    @Provides
    @Singleton
    fun provideGeocoder(@ApplicationContext context: Context): Geocoder {
        return Geocoder(context)
    }

    @Provides
    fun provideAddCheckoutUseCase(productRepository: ProductRepository): AddCheckoutUseCase =
        AddCheckoutUseCase(productRepository)

    @Provides
    fun provideAddNotificationUseCase(productRepository: ProductRepository) : AddNotificationUseCase =
        AddNotificationUseCase(productRepository)

    @Provides
    fun provideAddOrderUseCase(productRepository: ProductRepository) : AddOrderUseCase =
        AddOrderUseCase(productRepository)

    @Provides
    fun provideAddAddToCartUseCase(productRepository: ProductRepository) : AddToCartUseCase =
        AddToCartUseCase(productRepository)

    @Provides
    fun provideAddAddToFavouriteUseCase(productRepository: ProductRepository) : AddToFavouriteUseCase =
        AddToFavouriteUseCase(productRepository)

    @Provides
    fun provideAddAddUserLocationUseCase(productRepository: ProductRepository) : AddUserLocationUseCase =
        AddUserLocationUseCase(productRepository)

    @Provides
    fun provideGetAllCartItemsUseCase(productRepository: ProductRepository) : GetAllCartItemsUseCase =
        GetAllCartItemsUseCase(productRepository)

    @Provides
    fun provideGetAllFavouritesUseCase(productRepository: ProductRepository) : GetAllFavouritesUseCase =
        GetAllFavouritesUseCase(productRepository)

    @Provides
    fun provideGetAllNotificationsUseCase(productRepository: ProductRepository) : GetAllNotificationsUseCase =
        GetAllNotificationsUseCase(productRepository)    

    @Provides
    fun provideGetLatestCheckoutUseCase(productRepository: ProductRepository) : GetLatestCheckoutUseCase =
        GetLatestCheckoutUseCase(productRepository)
    @Provides
    fun provideGetLatestOrderUseCase(productRepository: ProductRepository) : GetLatestOrderUseCase =
        GetLatestOrderUseCase(productRepository)
    @Provides
    fun provideGetSessionUseCase(productRepository: ProductRepository) : GetSessionUseCase =
        GetSessionUseCase(productRepository)

    @Provides
    fun provideGetUserLocationByIdUseCase(productRepository: ProductRepository) : GetUserLocationByIdUseCase =
        GetUserLocationByIdUseCase(productRepository)
    @Provides
    fun provideGetUserNameUseCase(productRepository: ProductRepository) : GetUserNameUseCase =
        GetUserNameUseCase(productRepository)

    @Provides
    fun provideLoginUseCase(productRepository: ProductRepository) : LoginUseCase =
        LoginUseCase(productRepository)

    @Provides
    fun provideMarkNotificationAsReadUseCase(productRepository: ProductRepository) : MarkNotificationAsReadUseCase =
        MarkNotificationAsReadUseCase(productRepository)
    @Provides
    fun provideRegisterUseCase(productRepository: ProductRepository) : RegisterUseCase =
        RegisterUseCase(productRepository)

    @Provides
    fun provideSearchProductUseCase(productRepository: ProductRepository) : SearchProductUseCase =
        SearchProductUseCase(productRepository)

    @Provides
    fun provideGetProductByCategory(productRepository: ProductRepository) : GetProductByCategory =
        GetProductByCategory(productRepository)
}