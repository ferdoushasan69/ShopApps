package com.example.shopapps.presentation.ui.screen.signup

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapps.data.model.response.UserResponseItem
import com.example.shopapps.domain.model.user.UserResponseDomain
import com.example.shopapps.domain.usecase.RegisterUseCase
import com.example.shopapps.presentation.ui.common.Resource
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {
    var state = MutableStateFlow<Resource<String>?>(null)
        private set

    fun register(email: String, password: String,onResult:(Boolean)->Unit) {
        viewModelScope.launch {
            state.value = Resource.Loading
            try {
                val response =
                    registerUseCase.execute(password = password, email = email)
                state.value = Resource.Success(response)
                onResult(true)
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.d(TAG, "User already exists")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.d(TAG, "Invalid credentials")
            } catch (e: Exception) {
                Log.d(TAG, "Registration failed: ${e.message}")
            }
        }
    }

    fun resetUiState(){
        state.value = null
    }
}