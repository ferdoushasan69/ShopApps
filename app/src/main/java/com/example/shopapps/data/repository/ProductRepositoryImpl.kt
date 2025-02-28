package com.example.shopapps.data.repository


import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import androidx.lifecycle.map
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.log
import com.example.shopapps.data.local.dao.CartDao
import com.example.shopapps.data.local.dao.CheckoutDao
import com.example.shopapps.data.local.dao.FavoriteDao
import com.example.shopapps.data.local.dao.NotificationDao
import com.example.shopapps.data.local.dao.OrderDao
import com.example.shopapps.data.local.dao.UserLocationDao
import com.example.shopapps.data.local.entity.toCartDomain
import com.example.shopapps.data.local.entity.toDomainCheckOut
import com.example.shopapps.data.local.entity.toDomainFavorite
import com.example.shopapps.data.local.entity.toDomainNotification
import com.example.shopapps.data.local.entity.toDomainOrder
import com.example.shopapps.data.local.entity.toDomainUserLocation
import com.example.shopapps.data.local.entity.toEntityCart
import com.example.shopapps.data.local.entity.toEntityCheckOut
import com.example.shopapps.data.local.entity.toEntityFavorite
import com.example.shopapps.data.local.entity.toEntityNotification
import com.example.shopapps.data.local.entity.toEntityOrder
import com.example.shopapps.data.local.entity.toEntityUserLocation
import com.example.shopapps.data.remote.ApiService
import com.example.shopapps.data.utils.FirebaseManager
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.CheckOut
import com.example.shopapps.domain.model.Favorite
import com.example.shopapps.domain.model.LoginResponseDomain
import com.example.shopapps.domain.model.Notification
import com.example.shopapps.domain.model.Order
import com.example.shopapps.domain.model.UserLocation
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.model.user.UserResponseDomain
import com.example.shopapps.domain.repository.ProductRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val firebaseManager: FirebaseManager,
    private val apiService: ApiService,
    private val cartDao: CartDao,
    private val checkoutDao: CheckoutDao,
    private val favoriteDao: FavoriteDao,
    private val notificationDao: NotificationDao,
    private val orderDao: OrderDao,
    private val userLocationDao: UserLocationDao,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val geocoder: Geocoder
) : ProductRepository {

    override suspend fun getSession(): Flow<LoginResponseDomain> {
        return flow {
            val currentUser = firebaseManager.getCurrentUser()
            emit(LoginResponseDomain(token = currentUser?.uid ?: ""))
        }
    }

    override fun getUserName(): Flow<String> {
        return flow {
            val displayName = firebaseManager.getCurrentUser()?.displayName ?: ""
            emit(displayName)
        }
    }

    override suspend fun logOut() {
        firebaseManager.logout()
    }

    override suspend fun login(email: String, password: String): String {
        try {
            val result = firebaseManager.loginUser(email = email, password = password)
            if (result.isSuccess) {
                return firebaseManager.getCurrentUser()?.uid ?: "Unknown uid"
            } else {
                throw Exception("Login failed")
            }
        } catch (e: Exception) {
            throw Exception("Login Failed : ${e.message}")
        }

    }

    @SuppressLint("SuspiciousIndentation")
    override suspend fun register(email: String, password: String): String {
        try {
            val result = firebaseManager.registerUser(email = email, password = password)
            if (result.isSuccess) {
                return "Registration Successful"
            } else {
                throw Exception("Registration failed")
            }
        } catch (e: Exception) {
            throw Exception("Registration failed :${e.message}")
        }
    }


    //Notification
    override suspend fun addNotification(notification: Notification) {
        notificationDao.insertNotification(notification.toEntityNotification())
    }

    override fun getAllNotifications(): LiveData<List<Notification>> {
        return notificationDao.getAllNotification()
            .map { list -> list.map { it.toDomainNotification() } }
    }

    override suspend fun markAsRead(notificationId: Int) {
        notificationDao.markAsRead(notificationId)
    }

    override fun getUnReadNotification(): LiveData<Int> {
        return notificationDao.getUnReadNotificationCount()
    }

    //Checkout
    override suspend fun addCheckout(checkout: CheckOut) {
        checkoutDao.insertCheckOut(checkout.toEntityCheckOut())
    }

    override fun getLatestCheckout(): LiveData<CheckOut> {
        return checkoutDao.getLatestCheckOut()
            .map { checkOutEntity ->

                // Map to domain object if not null
                checkOutEntity.toDomainCheckOut()
            }
    }

    override fun getAllCheckout(): LiveData<List<CheckOut>> {
        return checkoutDao.getAllCheckOut().map { list -> list.map { it.toDomainCheckOut() } }
    }

    override suspend fun updatePaymentMethodCheckout(checkoutId: Int, paymentMethod: String) {
        checkoutDao.updatePaymentMethod(paymentMethod = paymentMethod, id = checkoutId)
    }

    //UserLocation

    override suspend fun addUsersLocation(userLocation: UserLocation) {
        userLocationDao.insertUserLocation(userLocation.toEntityUserLocation())
    }

    override suspend fun deleteUsersLocationById(id: Int) {
        userLocationDao.deleteUserLocationById(id)
    }

    override fun getAllUsersLocation(): LiveData<List<UserLocation>> {
        return userLocationDao.getAllUserLocation()
            .map { list -> list.map { it.toDomainUserLocation() } }
    }

    override fun getUserLocationById(id: Int): LiveData<UserLocation> {
        return userLocationDao.getUserLocationById(id).map { it.toDomainUserLocation() }
    }

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): LiveData<LatLng?> {
        val liveData = MutableLiveData<LatLng?>()
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                Log.d(TAG, "getCurrentLocation: ${location.latitude} ${location.longitude}")
                liveData.value = LatLng(location.latitude, location.longitude)
            } else {
                Log.d("VibeStoreRepository", "No location found")
            }
        }
        return liveData
    }

    override suspend fun getAddressFromLatLng(latLng: LatLng): String {
        return withContext(Dispatchers.IO) {
            val address = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            val result = address?.firstOrNull()
            result?.getAddressLine(0) ?: "Unknown Location"
        }
    }

    //Order
    override fun getLatestOrder(): LiveData<Order> {
        return orderDao.getLatestOrder().map { it.toDomainOrder() }
    }

    override suspend fun addOrder(order: Order) {
        orderDao.insertOrder(order.toEntityOrder())
    }

    //favorite
    override fun isProductFavorited(productId: Int): LiveData<Boolean> {
        return favoriteDao.isProductFavorited(productId)
    }

    override suspend fun addToFavourite(favourite: Favorite) {
        favoriteDao.insertFavorite(favourite.toEntityFavorite())
    }

    override fun getAllFavourites(): LiveData<List<Favorite>> {
        return favoriteDao.getAllFavorites().map { list -> list.map { it.toDomainFavorite() } }
    }

    override suspend fun deleteFavouriteById(id: Int) {
        favoriteDao.deleteFavoriteById(id)
    }


    //Cart

    override suspend fun updateCartById(cartId: Int, quantity: Int) {
        cartDao.updateQuantity(quantity = quantity, cartId = cartId)
    }

    override suspend fun deleteCartById(cartId: Int) {
        cartDao.deleteCartById(cartId)
    }

    override fun getAllCartItems(): LiveData<List<Cart>> {
        return cartDao.getAllCart().map { list -> list.map { it.toCartDomain() } }
    }

    override suspend fun addToCart(cart: Cart) {
        cartDao.insertCart(cart.toEntityCart())
    }

    //Products
    override suspend fun getAllProducts(limit: Int): List<ProductItem> {
        return apiService.getAllProducts(limit).map { it.toDomainProductItem() }
    }

    override suspend fun getProductByCategory(category: String, limit: Int): List<ProductItem> {
        return apiService.getProductsByCategory(category = category, limit = limit)
            .map { it.toDomainProductItem() }
    }

    override suspend fun getSingleProduct(id: Int): ProductItem {
        return apiService.getSingleProducts(id).toDomainProductItem()
    }

    override suspend fun sortProduct(sort: String): List<ProductItem> {
        return apiService.sortProducts(sort).map { it.toDomainProductItem() }
    }

    override suspend fun searchProduct(query: String): List<ProductItem> {
        val allProduct = apiService.getAllProducts(Int.MAX_VALUE)
        return allProduct
            .map { it.toDomainProductItem() }
    }
}