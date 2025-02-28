package com.example.shopapps.presentation.ui.navigation

import androidx.lifecycle.ViewModel
import com.example.shopapps.data.utils.FirebaseManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseManager: FirebaseManager
):ViewModel() {
    private val _isUserLoggedIn = MutableStateFlow(firebaseManager.isUserLoggedIn())
    val isUserLoggedIn = _isUserLoggedIn.asStateFlow()
}