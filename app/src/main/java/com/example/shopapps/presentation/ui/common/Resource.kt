package com.example.shopapps.presentation.ui.common

sealed class Resource<out T : Any?> {
    data object Loading : Resource<Nothing>()
    data class Success<out T : Any?>(val data: T) : Resource<T>()
    data class Error(val errorMsg: String) : Resource<Nothing>()
}