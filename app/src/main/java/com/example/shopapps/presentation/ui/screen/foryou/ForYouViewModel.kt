package com.example.shopapps.presentation.ui.screen.foryou

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.usecase.AddToCartUseCase
import com.example.shopapps.domain.usecase.GetProductByCategory
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ForYouViewModel @Inject constructor(
    private val getProductByCategory: GetProductByCategory,
    private val addToCartUseCase: AddToCartUseCase
):ViewModel() {
    var uiState : MutableLiveData<Resource<List<ProductItem>>> = MutableLiveData()
        private set

    fun getProductByCategory(category : String,limit : Int){
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                val product  = getProductByCategory.execute(category = category, limit = limit)
                uiState.value= Resource.Success(product)
            }catch (e:Exception){
                uiState.value = Resource.Error(e.message.toString())
                Log.d(TAG, "getProductByCategory: ${e.printStackTrace()}")
            }
        }
    }

    fun addToCart(product : ProductItem){
        viewModelScope.launch {
            val cartItems = Cart(
                productId = product.id,
                productName = product.title,
                productPrice = product.price.toString(),
                productImage = product.image,
                productCategory = product.category,
                productQuantity =1
            )
            addToCartUseCase.execute(cartItems)
        }
    }
}