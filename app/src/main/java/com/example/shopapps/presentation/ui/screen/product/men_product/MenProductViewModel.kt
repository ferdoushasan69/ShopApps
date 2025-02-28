package com.example.shopapps.presentation.ui.screen.product.men_product

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
class MenProductViewModel @Inject constructor(
    private val getProductByCategory: GetProductByCategory,
    private val addToCartUseCase: AddToCartUseCase
):ViewModel() {
    var uiState : MutableLiveData<Resource<List<ProductItem>>> = MutableLiveData(Resource.Loading)
        private set

    fun getProductByCategory(category : String,limit : Int){
        viewModelScope.launch{
            uiState.value  = Resource.Loading
            try {
                val product = getProductByCategory.execute(category = category, limit = limit)
                uiState.value  = Resource.Success(product)
            }catch (e:Exception){
                uiState.value = Resource.Error(e.message.toString())
                Log.d(TAG, "MenProductViewModel getProductByCategory Error : ${e.message}")
            }
        }
    }

    fun addToCart(productItem: ProductItem){
        viewModelScope.launch {
            val cartItems = Cart(
                productId = productItem.id,
                productName = productItem.title,
                productPrice = productItem.price.toString(),
                productImage = productItem.image,
                productCategory = productItem.category,
                productQuantity = 1
            )
        }
    }
}