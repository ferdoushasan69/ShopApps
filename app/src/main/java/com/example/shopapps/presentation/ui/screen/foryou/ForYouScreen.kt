package com.example.shopapps.presentation.ui.screen.foryou

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ProductCard2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ForYouScreen(
    navigateDetail:(Int)->Unit,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    modifier: Modifier =Modifier,
    viewModel: ForYouViewModel= hiltViewModel()
) {

    viewModel.uiState.observeAsState(initial = Resource.Loading).value.let { uiState->
        when(uiState){
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = uiState.errorMsg)
                }
            }
            is Resource.Loading -> {
                viewModel.getProductByCategory("women's clothing",6)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(275.dp)
                ) {
                    LazyRow {
                        items(4){
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }
            is Resource.Success -> {
                val forYouProduct = uiState.data
                LazyRow(modifier = Modifier){
                    items(forYouProduct){product->
                        ProductCard2(
                            modifier = Modifier.clickable {
                                navigateDetail(product.id)
                            },
                            image = product.image,
                            title = product.title,
                            rating = product.rating.rate.toString(),
                            price = product.price.toString(),
                            category = product.category,
                            addToCart = {
                                viewModel.addToCart(product)
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Product added to cart"
                                    )
                                }
                            },
                        )

                    }
                }
            }
        }
    }
}