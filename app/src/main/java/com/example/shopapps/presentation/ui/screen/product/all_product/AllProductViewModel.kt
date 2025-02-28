package com.example.shopapps.presentation.ui.screen.product.all_product

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.shopapps.data.pagination.ProductPagingSource
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.usecase.AddToCartUseCase
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllProductViewModel @Inject constructor(
    private val addToCartUseCase: AddToCartUseCase,
    private val repository: ProductRepository
) : ViewModel() {


    val pagedProducts: Flow<PagingData<ProductItem>> =
        repository.getPagedProducts().cachedIn(viewModelScope)
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
             addToCartUseCase.execute(cartItems)
         }
    }

    fun deleteProductById(id:Int){
        viewModelScope.launch {
            val deleteItems =  repository.deleteCartById(id)
        }
    }
}