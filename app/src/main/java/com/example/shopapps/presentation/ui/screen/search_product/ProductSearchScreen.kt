package com.example.shopapps.presentation.ui.screen.search_product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ProductCard2
import com.example.shopapps.presentation.ui.component.SearchBar
import com.example.shopapps.presentation.ui.navigation.ProductDetails
import kotlinx.coroutines.launch

@Composable
fun ProductSearchScreen(
    navController: NavHostController,
    viewModel: ProductSearchViewModel = hiltViewModel()
) {
    val sortValue = remember { mutableStateOf("asc") }
    val uiState by viewModel.uiState.observeAsState(initial = Resource.Loading)
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(

                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(it.visuals.message)
                }
            }
        },
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            SearchBar(
                onSearchChange = {query->
                    if (query.isEmpty()) {
                        viewModel.getAllProducts(20)
                    }else{
                        viewModel.searchProduct(query)
                    }
                }
            )
        }
    ) {innerPadding->
        when(uiState){
            is Resource.Error -> {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .fillMaxSize()
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                            .align(Alignment.Center)
                    ) {
                       Image(painterResource(R.drawable.image_no_product_found),contentDescription = null)
                    }
                }
            }
            is Resource.Loading ->{
                viewModel.getAllProducts(20)
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)){
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(196.dp),
                        modifier = Modifier.heightIn(min=1000.dp, max = 1000.dp)
                    ) {
                        item(10){
                            AnimatedShimmerProduct()
                        }
                    }
                }
            }
            is Resource.Success -> {
                val product = (uiState as Resource.Success<List<ProductItem>>).data
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(196.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(product){product->
                        ProductCard2(
                            modifier = Modifier.clickable {
                                navController.navigate(ProductDetails(product.id))
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
                            }
                        )
                    }
                }
            }
        }

    }
}