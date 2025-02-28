package com.example.shopapps.presentation.ui.screen.product.all_product


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ProductCard2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun AllProductScreen(
    gridHeight: Dp = Dp.Unspecified,
    limit: Int,
    height: Dp,
    count: Int = 4,
    navigateToDetail: (Int) -> Unit,
    allProductViewModel: AllProductViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val pageProduct = allProductViewModel.pagedProducts.collectAsLazyPagingItems()
    LazyVerticalGrid(
        columns = GridCells.Adaptive(196.dp),
        modifier = Modifier.heightIn(min = gridHeight, max = gridHeight)
    ) {
        items(pageProduct.itemCount) { index ->
            val product = pageProduct[index]
            if (product != null) {
                ProductCard2(
                    image = product.image,
                    title = product.title,
                    category = product.category,
                    price = product.price.toString(),
                    rating = product.rating.rate.toString(),
                    modifier = Modifier.clickable {
                        navigateToDetail(product.id)
                    },
                    addToCart = {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Product added to cart",
                            )
                        }
                        Toast.makeText(context, "Product Added to cart", Toast.LENGTH_SHORT).show()
                        allProductViewModel.addToCart(product)

                    }
                )
            }
        }
    }
    when (val refreshState = pageProduct.loadState.refresh) {
        is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    refreshState.error.message ?: "en occur error",
                    modifier = Modifier.padding(15.dp)
                )
            }
        }

        LoadState.Loading -> {
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
                    items(count) {
                        AnimatedShimmerProduct()
                    }
                }
            }
        }

        else -> {}
    }

}