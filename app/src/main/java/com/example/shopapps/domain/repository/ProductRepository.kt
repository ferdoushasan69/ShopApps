package com.example.shopapps.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.CheckOut
import com.example.shopapps.domain.model.Favorite
import com.example.shopapps.domain.model.LoginResponseDomain
import com.example.shopapps.domain.model.Notification
import com.example.shopapps.domain.model.Order
import com.example.shopapps.domain.model.UserLocation
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.model.user.UserResponseDomain
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getPagedProducts(): Flow<PagingData<ProductItem>>

    //Authentication
    suspend fun getSession(): Flow<LoginResponseDomain>

     fun getUserName(): Flow<String>

    suspend fun logOut()

    suspend fun login(email : String,password: String): String

    suspend fun register(email :String,password: String): String

    // Notification
    suspend fun addNotification(notification: Notification)
    fun getAllNotifications(): LiveData<List<Notification>>
    suspend fun markAsRead(notificationId: Int)
    fun getUnReadNotification(): LiveData<Int>

    // Checkout
    suspend fun addCheckout(checkout: CheckOut)
    fun getLatestCheckout(): LiveData<CheckOut>
    fun getAllCheckout(): LiveData<List<CheckOut>>
    suspend fun updatePaymentMethodCheckout(checkoutId: Int, paymentMethod: String)

    // User Location
    suspend fun addUsersLocation(userLocation: UserLocation)
    suspend fun deleteUsersLocationById(id: Int)
    fun getAllUsersLocation(): LiveData<List<UserLocation>>
    fun getUserLocationById(id: Int): LiveData<UserLocation>
    fun getCurrentLocation(): LiveData<LatLng?>
    suspend fun getAddressFromLatLng(latLng: LatLng): String

    // Order
    fun getLatestOrder(): LiveData<Order>
    suspend fun addOrder(order: Order)

    // Favourite Product
    fun isProductFavorited(productId: Int): LiveData<Boolean>
    suspend fun addToFavourite(favourite: Favorite)
    fun getAllFavourites(): LiveData<List<Favorite>>
    suspend fun deleteFavouriteById(id: Int)

    // Cart Item
    suspend fun updateCartById(cartId: Int, quantity: Int)
    suspend fun deleteCartById(cartId: Int)
    fun getAllCartItems(): LiveData<List<Cart>>
    suspend fun addToCart(cart: Cart)

    // Product from API
    suspend fun getAllProducts(limit: Int): List<ProductItem>
    suspend fun getProductByCategory(category: String, limit: Int): List<ProductItem>
    suspend fun getSingleProduct(id: Int): ProductItem
    suspend fun sortProduct(sort: String): List<ProductItem>
    suspend fun searchProduct(query: String): List<ProductItem>
}