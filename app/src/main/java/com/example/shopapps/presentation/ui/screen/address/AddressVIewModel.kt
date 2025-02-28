package com.example.shopapps.presentation.ui.screen.address

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shopapps.domain.model.UserLocation
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressVIewModel @Inject constructor(
    private val repository: ProductRepository
):ViewModel() {
    private var _userLocation : MutableLiveData<Resource<UserLocation>> = MutableLiveData()
    val userLocation : LiveData<Resource<UserLocation>> get() = _userLocation

    fun addUserLocation(userLocation: UserLocation){
        _userLocation.value = Resource.Success(userLocation)
    }

}