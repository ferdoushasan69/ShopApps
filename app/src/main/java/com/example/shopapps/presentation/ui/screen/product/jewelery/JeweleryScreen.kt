package com.example.shopapps.presentation.ui.screen.product.jewelery

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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ProductCard2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun JeweleryScreen(
    gridHeight: Dp = Dp.Unspecified,
    limit: Int,
    count: Int = 4,
    height: Dp,
    navigateToDetail: (Int) -> Unit,
    scope: CoroutineScope,
    viewModel: JeweleryViewModel = hiltViewModel(),
    snackBarHostState: SnackbarHostState
) {
    viewModel.uiState.observeAsState(initial = Resource.Loading).value.let { uiState ->
        when (uiState) {
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(uiState.errorMsg)
                }
            }

            is Resource.Loading -> {
                viewModel.getJeweleryProductByCategory("jewelery", limit)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .height(height)
                ) {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(196.dp),
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .heightIn(min = gridHeight, max = gridHeight)
                    ) {
                        item(count) {
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }

            is Resource.Success -> {
                val jeweleryProduct = uiState.data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(196.dp),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .heightIn(min = gridHeight, max = gridHeight)
                ) {
                    items(jeweleryProduct){products->
                        ProductCard2(
                            modifier = Modifier.clickable {
                                navigateToDetail(products.id)
                            },
                            image = products.image,
                            title = products.title,
                            rating = products.rating.rate.toString(),
                            price = products.price.toString(),
                            category = products.category,
                            addToCart = {
                                viewModel.addToJeweleryCart(products)
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