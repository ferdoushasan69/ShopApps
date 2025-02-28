package com.example.shopapps.presentation.ui.screen.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.shopapps.R
import com.example.shopapps.domain.model.Favorite
import com.example.shopapps.presentation.ui.navigation.ProductDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val favoriteItems by viewModel.allFavorites.observeAsState(emptyList())
    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                title = {
                    Text(
                        text = stringResource(R.string.favourite),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
        ) {
            if (favoriteItems.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .align(Alignment.Center)
                ) {

                    Text(
                        text = "You don't have any favourite items",
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            } else {
                FavoriteContent(
                    navHostController = navHostController,
                    favoriteList = favoriteItems
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    navHostController: NavHostController,
    favoriteList: List<Favorite>
) {

    var visible = remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible.value = true
    }

    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(favoriteList) { product ->
            AnimatedVisibility(
                modifier = Modifier.animateItemPlacement(tween(100)),
                visible = visible.value,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            ) {
                FavouriteItem(
                    imageId = product.productImage,
                    productName = product.productName,
                    category = product.productCategory,
                    price = product.productPrice,
                    modifier = Modifier
                        .clickable {
                            navHostController.navigate(ProductDetails(product.productId))
                        }
                )
            }
        }
    }

}

@Composable
fun FavouriteItem(
    imageId: String,
    productName: String,
    category: String,
    price: String,
    modifier: Modifier=Modifier
) {
    Row(
        modifier = modifier
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .height(100.dp)
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.background
            )
    ) {
        AsyncImage(
            modifier = Modifier.size(
                height = 100.dp,
                width = 100.dp
            ),
            model = imageId,
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = productName,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 2
            )
            Text(
                text = "Category : $category",
                fontSize = 12.sp,
                maxLines = 1,
                color = MaterialTheme.colorScheme.outline
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            Text(
                text = "$$price",
                fontWeight = FontWeight.Medium
            )
        }
        Icon(
            painter = painterResource(R.drawable.icon_favourite_filled_red),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier
                .padding(
                    end = 8.dp,
                    bottom = 4.dp,

                    )
                .align(Alignment.Bottom)
        )
    }
}
