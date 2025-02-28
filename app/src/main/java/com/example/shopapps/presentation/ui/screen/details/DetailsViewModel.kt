package com.example.shopapps.presentation.ui.screen.details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.Favorite
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {
    var uiState: MutableLiveData<Resource<ProductItem>> = MutableLiveData(Resource.Loading)
        private set

    var favoriteItems : LiveData<List<Favorite>> = repository.getAllFavourites()

    fun getSinglyProductById(id: Int) {
        viewModelScope.launch {
            uiState.value = Resource.Loading
            try {
                val product = repository.getSingleProduct(id)
                uiState.value = Resource.Success(product)
            } catch (e: Exception) {
                uiState.value = Resource.Error(e.message.toString())
                Log.d(TAG, "DetailsViewModel getSinglyProductById Error : ${e.message}")
            }
        }
    }

    fun addToCart(productItem: ProductItem) {
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

    fun addToFavorite(productItem: ProductItem) {
        viewModelScope.launch {
            val favoriteItems = Favorite(
                productId = productItem.id,
                productName = productItem.title,
                productPrice = productItem.price.toString(),
                productImage = productItem.image,
                productCategory = productItem.category,
                productQuantity = 1
            )
            repository.addToFavourite(favoriteItems)
        }
    }

    fun isProductFavorited(productId: Int): LiveData<Boolean> {
        return repository.isProductFavorited(productId)

    }
    fun deleteFavoriteById(favorite: Favorite){
       viewModelScope.launch {
            repository.deleteFavouriteById(favorite.id)
       }
    }
}