package com.example.shopapps.presentation.ui.screen.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.shopapps.R
import com.example.shopapps.domain.model.products.ProductItem
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AnimatedShimmerDetailsProduct
import com.example.shopapps.presentation.ui.component.AnimatedShimmerProduct
import com.example.shopapps.presentation.ui.component.ExpandingText
import com.example.shopapps.presentation.ui.component.RatingBar
import com.example.shopapps.presentation.ui.theme.primary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    productId: Int,
    navHostController: NavHostController,
    viewModel: DetailsViewModel = hiltViewModel(),
) {

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val uiState by viewModel.uiState.observeAsState(initial = Resource.Loading)
    val favoriteItems by viewModel.favoriteItems.observeAsState(initial = emptyList())
    val isProductsFavorited by viewModel.isProductFavorited(productId).observeAsState(false)

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.product_details),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp
                    )
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.clickable {
                            navHostController.navigateUp()
                        }
                    )
                },
                actions = {
                    Icon(
                        painter = painterResource(id = if (isProductsFavorited) R.drawable.icon_favourite_filled_red else R.drawable.icon_favourite_outlined),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.clickable {
                            if (isProductsFavorited) {
                                favoriteItems.find { it.productId == productId }?.let { favorite ->
                                    viewModel.deleteFavoriteById(favorite)
                                    scope.launch {
                                        snackBarHostState.showSnackbar(
                                            message = "Product removed from favourites"
                                        )
                                    }
                                }
                            } else {
                                viewModel.addToFavorite((uiState as Resource.Success<ProductItem>).data)
                                scope.launch {
                                    snackBarHostState.showSnackbar(
                                        message = "Product add to favorite"
                                    )
                                }
                            }
                        },
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) { innerPadding ->
        when (uiState) {
            is Resource.Error -> {
                Box(modifier = Modifier.fillMaxSize()){
                    Text(text = (uiState as Resource.Error).errorMsg)
                }
            }
            is Resource.Loading -> {
                AnimatedShimmerDetailsProduct(
                    modifier = Modifier.padding(innerPadding)
                )
                viewModel.getSinglyProductById(productId)
            }

            is Resource.Success -> {
                val data = (uiState as Resource.Success<ProductItem>).data
                DetailsContent(
                    productItem = data,
                    addToCart = {
                        viewModel.addToCart(productItem = data)
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Product added to cart"
                            )
                        }
                    },
                    rating = data.rating.rate.toString(),
                    paddingValues =innerPadding
                )
            }
        }
    }

}

@Composable
fun DetailsContent(
    productItem: ProductItem,
    addToCart: () -> Unit,
    rating: String,
    paddingValues: PaddingValues
) {

    val ratingState by remember { mutableFloatStateOf(rating.toFloat()) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 90.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = productItem.image,
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = productItem.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 22.sp
            )
            Spacer(Modifier.height(8.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                RatingBar(rating = ratingState, modifier = Modifier.padding(end = 4.dp))
                Text(
                    "${productItem.rating.rate} /5",
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .offset(y = 3.dp)
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(20.dp)
                        .width(1.dp)
                )
                Text(
                    modifier = Modifier
                        .offset(y = 3.dp),
                    text = "(${productItem.rating.count})",
                    color = MaterialTheme.colorScheme.outline,
                    fontWeight = FontWeight.Light
                )
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .height(20.dp)
                        .width(1.dp)
                )
                Text(
                    modifier = Modifier
                        .offset(y = 3.dp),
                    maxLines = 1,
                    fontSize = 14.sp,
                    text = productItem.category,
                    color = primary,
                )
            }
            Text(
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 32.dp),
                text = stringResource(R.string.description_product),
            )
            ExpandingText(
                modifier = Modifier.padding(top = 8.dp),
                text = productItem.description,
                fontSize = (14).sp
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(85.dp)
                .background(MaterialTheme.colorScheme.background)
        ) {
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    "$${productItem.price}",
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 40.sp
                )
                Button(
                    modifier = Modifier
                        .height(55.dp)
                        .width(170.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = addToCart,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                      Text("Add To Cart")
                        Spacer(Modifier.width(5.dp))
                        Icon(Icons.Default.ShoppingCart,contentDescription = null)
                    }
                }
            }
        }
    }
}
