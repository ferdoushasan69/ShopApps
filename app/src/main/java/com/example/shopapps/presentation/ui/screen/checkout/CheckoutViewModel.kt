package com.example.shopapps.presentation.ui.screen.checkout

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.CheckOut
import com.example.shopapps.domain.model.Notification
import com.example.shopapps.domain.model.Order
import com.example.shopapps.domain.model.stripe_response.PaymentIntentModel
import com.example.shopapps.domain.repository.PaymentRepository
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.presentation.ui.common.Resource
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val repository: ProductRepository,
    private val paymentRepository: PaymentRepository

) : ViewModel() {
    val orderItems: LiveData<Order> = repository.getLatestOrder()
    private var _selectedShippingId: MutableLiveData<Int?> = MutableLiveData<Int?>()
    val selectedShippingId: LiveData<Int?> get() = _selectedShippingId

    private var _selectedCouponId: MutableLiveData<Int?> = MutableLiveData<Int?>()
    val selectedCouponId: LiveData<Int?> get() = _selectedCouponId



        fun selectedCouponId(id: Int) {
            _selectedCouponId.value = id
        }


        fun addNotification(
            message: String, notificationType: String, firstProductImage: String,
            firstProductName: String, quantityCheckOut: Int
        ) {
            val notificationModel = Notification(
                notificationType = notificationType,
                firstProductName = firstProductName,
                quantityCheckout = quantityCheckOut,
                firstProductImage = firstProductImage,
                message = message,
            )
            viewModelScope.launch {
                repository.addNotification(notificationModel)
            }
        }

        fun addCheckOut(
            receiverName: String, receiverAddress: String, orderItems: List<Cart>,
            shippingMethod: String,
            shippingCost: Double,
            shippingDescription: String,
            paymentMethod: String = "",
            coupon: String = "",
            totalPrice: Double
        ) {
            val checkOut = CheckOut(
                receiverName = receiverName,
                receiverAddress = receiverAddress,
                orderItems = orderItems,
                shippingMethod = shippingMethod,
                coupon = coupon,
                shippingCost = shippingCost,
                shippingDescription = shippingDescription,
                paymentMethod = paymentMethod,
                totalPrice = totalPrice
            )
            viewModelScope.launch {
                repository.addCheckout(checkOut)
            }
        }


        fun selectShipping(id: Int) {
            _selectedShippingId.value = id
        }


}