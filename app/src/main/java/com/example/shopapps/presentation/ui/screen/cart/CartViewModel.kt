package com.example.shopapps.presentation.ui.screen.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.Order
import com.example.shopapps.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    val cartItems: LiveData<List<Cart>> = repository.getAllCartItems()
    var selectedCartItems: MutableLiveData<Set<Cart>> = MutableLiveData<Set<Cart>>(emptySet())
        private set

    var totalPrice: LiveData<Double> = selectedCartItems.map { item ->
        item.sumOf { it.productPrice.toDouble() * it.productQuantity }
    }

    fun updateQuantity(cart: Cart, quantity: Int) {

        viewModelScope.launch {
            if (quantity > 0) {
                repository.updateCartById(cart.id, quantity)
            } else {
                repository.deleteCartById(cart.id)
            }

            val currentCartItem = selectedCartItems.value.orEmpty().toMutableSet()
            if (currentCartItem.contains(cart)) {
                val updateCart = cart.copy(productQuantity = quantity)
                currentCartItem.remove(cart)
                if (quantity > 0) currentCartItem.add(updateCart)
                selectedCartItems.value = currentCartItem
            }
        }
    }

    fun createOrderFromSelectedItems() {
        val selectedItem = selectedCartItems.value.orEmpty()
        if (selectedItem.isNotEmpty()) {
            val totalPrice = selectedItem.sumOf { it.productPrice.toDouble() * it.productQuantity }
            val order = Order(
                totalPrice = totalPrice,
                items = selectedItem.toList()
            )
            viewModelScope.launch {
                repository.addOrder(order)
                selectedItem.forEach { cart ->
                    repository.deleteCartById(cart.id)
                }
                selectedCartItems.value = emptySet()
            }
        }
    }

    fun updateCheckedItem(cart: Cart, isItemSelected: Boolean) {
        val currentItems = selectedCartItems.value.orEmpty().toMutableSet()
        if (isItemSelected){
            currentItems.add(cart)
        }else{
            currentItems.remove(cart)
        }
        selectedCartItems.value = currentItems
    }
}