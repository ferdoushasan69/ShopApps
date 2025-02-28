package com.example.shopapps.presentation.ui.screen.product.electronics

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ProductCard2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ElectronicsScreen(
    gridHeight : Dp = Dp.Unspecified,
    limit : Int,
    count : Int = 4,
    height : Dp,
    viewModel: ElectronicViewModel= hiltViewModel(),
    scope: CoroutineScope,
    navigateToDetail:(Int)->Unit,
    snackBarHostState: SnackbarHostState
) {

    viewModel.uiState.observeAsState(initial = Resource.Loading).value.let { uiState->
        when(uiState){
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(uiState.errorMsg)
                }
            }
            is Resource.Loading ->{
                viewModel.getElectronicProductByCategory("electronics",limit)
                Box(modifier = Modifier. fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .height(height)){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(196.dp),
                        modifier = Modifier.background(MaterialTheme.colorScheme.background)
                            .heightIn(min = gridHeight,max=gridHeight)
                    ) {
                        item(count){
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }
            is Resource.Success -> {
                val electronicsProducts = uiState.data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(196.dp),
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                        .heightIn(min = gridHeight,max=gridHeight)
                ) {
                    items(electronicsProducts){product->
                        ProductCard2(
                            modifier = Modifier.clickable {
                                navigateToDetail(product.id)
                            },
                            image = product.image,
                            title = product.title,
                            rating = product.rating.rate.toString(),
                            price = product.price.toString(),
                            category = product.category,
                            addToCart = {
                                viewModel.addToElectronicCart(product)
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Product added to cart",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}