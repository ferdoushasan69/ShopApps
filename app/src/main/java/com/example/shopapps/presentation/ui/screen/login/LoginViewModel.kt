package com.example.shopapps.presentation.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.usecase.LoginUseCase
import com.example.shopapps.presentation.ui.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    var state = MutableStateFlow<Resource<String>?>(null)
        private set

    fun login(
        email: String,
        password: String,
        onResult:(Boolean)->Unit
    ) {
        viewModelScope.launch {
            state.value = Resource.Loading
            try {

                val response = loginUseCase.execute(email = email, password = password)
                state.value = Resource.Success(response)
                onResult(true)
            } catch (e: Exception) {
                e.printStackTrace()
                state.value = Resource.Error(e.message ?: "unknown error login viewmodel")
                onResult(false)
            }
        }
    }

    fun resetUiState(){
        state.value = null
    }
}