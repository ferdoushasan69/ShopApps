package com.example.shopapps.presentation.ui.screen.search_product

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.usecase.GetAllProductsUseCase
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val getAllProductsUseCase: GetAllProductsUseCase,
    private val repository: ProductRepository
):ViewModel() {

    var uiState : MutableLiveData<Resource<List<ProductItem>>> = MutableLiveData(Resource.Loading)
        private set

    private var allProducts : List<ProductItem> =  emptyList()

    fun getAllProducts(limit : Int){
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                val products = getAllProductsUseCase.execute(limit)
                allProducts = products
                uiState.value = Resource.Success(products)
            }catch (e:Exception){
                Log.d(TAG, "ProductSearchViewModel getAllProducts Error : ${e.message}")
                uiState.value = Resource.Error(e.message.toString())
            }
        }
    }

    fun sortProduct(sort:String){
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                val sortProducts = repository.sortProduct(sort)
                uiState.value = Resource.Success(sortProducts)
            }catch (e:Exception){
                Log.d(TAG, "ProductSearchViewModel sortProduct Error : ${e.message}")
                uiState.value = Resource.Error(e.message.toString())
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
            repository.addToCart(cartItems)
        }
    }

    fun searchProduct(query : String){
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                if (query.isEmpty()){
                    uiState.value = Resource.Success(allProducts)
                }
                else{
                  val filterProducts = allProducts.filter {
                      it.title.contains(query.trim(), ignoreCase = true)
                  }
                    if (filterProducts.isEmpty()){
                        uiState.value = Resource.Error("Product not found")
                    }else{
                    uiState.value= Resource.Success(filterProducts)
                    }
                }
            }catch (e:Exception){
                Log.d(TAG, "ProductSearchViewModel searchProduct Error : ${e.message}")
                uiState.value = Resource.Error(e.message.toString())
            }
        }
    }
}