package com.example.shopapps.presentation.ui.screen.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopapps.domain.model.Notification
import com.example.shopapps.domain.usecase.AddNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val addNotificationUseCase: AddNotificationUseCase
) : ViewModel() {


    fun addNotification(notification: Notification) {
        viewModelScope.launch {
            addNotificationUseCase.execute(notification)
        }
    }
}