package com.example.shopapps.presentation.ui.screen.home
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.repository.ProductRepository
import com.example.shopapps.domain.usecase.GetAllCartItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val productRepository: ProductRepository
): ViewModel() {

     val cartItems : LiveData<List<Cart>> = getAllCartItemsUseCase.execute()

    val unReadNotificationCount : LiveData<Int> = productRepository.getUnReadNotification()


}