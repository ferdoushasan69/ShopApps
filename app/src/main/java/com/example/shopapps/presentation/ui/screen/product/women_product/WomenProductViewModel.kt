package com.example.shopapps.presentation.ui.screen.product.women_product

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
class WomenProductViewModel @Inject constructor(
    private val getProductByCategory: GetProductByCategory,
    private val addToCartUseCase: AddToCartUseCase
):ViewModel() {
    var uiState : MutableLiveData<Resource<List<ProductItem>>> = MutableLiveData(Resource.Loading)
        private set

    fun getWomenProductByCategory(category : String,limit : Int){
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                val womenProduct = getProductByCategory.execute(category = category,limit)
                uiState.value = Resource.Success(womenProduct)
            }catch (e:Exception){
                uiState.value = Resource.Error(e.message.toString())
                Log.d(TAG, "WomenProductViewModel getWomenProductByCategory: ${e.message}")
            }
        }
    }

    fun addToCartWomenProduct(productItem: ProductItem){
        viewModelScope.launch {
            val womenCartItems = Cart(
                productId = productItem.id,
                productName = productItem.title,
                productPrice = productItem.price.toString(),
                productImage = productItem.image,
                productCategory = productItem.category,
                productQuantity = 1
            )
            addToCartUseCase.execute(womenCartItems)
        }
    }
}