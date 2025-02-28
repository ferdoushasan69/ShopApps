package com.example.shopapps.presentation.ui.screen.profile

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val  repository: ProductRepository
):ViewModel() {

     fun getUserName():LiveData<String>{
         Log.d(TAG, "getUserName: ${repository.getUserName()}")
         return repository.getUserName().asLiveData()
    }

    fun logout(){
        viewModelScope.launch {
            withContext(NonCancellable){
                repository.logOut()
            }
        }
    }
}