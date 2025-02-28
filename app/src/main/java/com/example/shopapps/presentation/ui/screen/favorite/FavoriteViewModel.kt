package com.example.shopapps.presentation.ui.screen.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopapps.domain.model.Favorite
import com.example.shopapps.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: ProductRepository
):ViewModel() {
    val allFavorites : LiveData<List<Favorite>> = repository.getAllFavourites()
}