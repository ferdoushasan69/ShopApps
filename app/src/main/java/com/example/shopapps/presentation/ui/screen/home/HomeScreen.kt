package com.example.shopapps.presentation.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.presentation.ui.component.HomeSection
import com.example.shopapps.presentation.ui.component.ImageSlider
import com.example.shopapps.presentation.ui.component.TabCategory
import com.example.shopapps.presentation.ui.navigation.Category
import com.example.shopapps.presentation.ui.navigation.MyCart
import com.example.shopapps.presentation.ui.navigation.Notification
import com.example.shopapps.presentation.ui.navigation.ProductDetails
import com.example.shopapps.presentation.ui.screen.foryou.ForYouScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val cartItem by homeViewModel.cartItems.observeAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val unReadNotification by homeViewModel.unReadNotificationCount.observeAsState(0)
    val scope = rememberCoroutineScope()
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) {
                Snackbar(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(it.visuals.message)
                }
            }
        },
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("") },
                navigationIcon = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(45.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "Online",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(
                                    start = 8.dp,
                                    top = 8.dp
                                ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Store",
                            fontSize = 18.sp,
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 8.dp
                                ),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    BadgedBox(badge = {
                        if (unReadNotification > 0) {
                            Badge(
                                contentColor = Color.Red,
                                modifier = Modifier
                                    .size(10.dp)
                                    .offset(x = (-6).dp, y = 7.dp)
                            )
                        }
                    }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.icon_notification_outlined),
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                navController.navigate(Notification)
                            })
                    }
                    Spacer(Modifier.width(16.dp))
                    BadgedBox(badge = {
                        if (cartItem?.isNotEmpty() == true) {
                            Badge(
                                contentColor = Color.White,
                                containerColor = Color.Red,
                                modifier = Modifier.offset(x = (-20).dp, y = 7.dp)
                            ) {
                                Text(cartItem?.size.toString())

                            }
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.icon_cart_outlined),
                            contentDescription = "Cart",
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .clickable {
                                    navController.navigate(MyCart)
                                }
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background,
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            item {
                HomeSection(
                    title = "",
                    content = { ImageSlider() },
                    navigateToSeeAll = {}
                )
            }

            item {
                HomeSection(
                    title = stringResource(R.string.categories),
                    content = {
                        TabCategory(
                            limit = 4,
                            gridHeight = 640.dp,
                            snackBarHostState = snackBarHostState,
                            scope = scope,
                            navigateToDetail = {
                                navController.navigate(ProductDetails(productId = it))
                            }
                        )
                    },
                    navigateToSeeAll = {
                        navController.navigate(Category)
                    }
                )

            }
            item {
                HomeSection(
                    title = stringResource(R.string.for_you),
                    content = {
                        ForYouScreen(
                            navigateDetail = { productId ->
                                navController.navigate(ProductDetails(productId))
                            },
                            snackBarHostState = snackBarHostState,
                            scope = scope,
                        )
                    },
                    navigateToSeeAll = {}
                )
            }
        }
    }
}