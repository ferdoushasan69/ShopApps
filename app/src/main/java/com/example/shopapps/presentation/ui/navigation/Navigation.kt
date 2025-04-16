package com.example.shopapps.presentation.ui.navigation

import AddressScreen
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.shopapps.R
import com.example.shopapps.presentation.ui.component.AddressItemScreen
import com.example.shopapps.presentation.ui.screen.cart.CartScreen
import com.example.shopapps.presentation.ui.screen.category.CategoryScreen
import com.example.shopapps.presentation.ui.screen.checkout.CheckOutScreen
import com.example.shopapps.presentation.ui.screen.coupon.CouponScreen
import com.example.shopapps.presentation.ui.screen.details.DetailScreen
import com.example.shopapps.presentation.ui.screen.favorite.FavoriteScreen
import com.example.shopapps.presentation.ui.screen.home.HomeScreen
import com.example.shopapps.presentation.ui.screen.login.LoginScreen
import com.example.shopapps.presentation.ui.screen.notification.NotificationScreen
import com.example.shopapps.presentation.ui.screen.profile.ProfileScreen
import com.example.shopapps.presentation.ui.screen.search_product.ProductSearchScreen
import com.example.shopapps.presentation.ui.screen.signup.SignUpScreen
import com.example.shopapps.presentation.ui.screen.success.SuccessPaymentScreen
import com.example.shopapps.presentation.ui.screen.welcome.WelcomeScreen
import com.example.shopapps.presentation.ui.theme.iconColor


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Navigation(authViewModel: AuthViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    val isUserLoggedIn by authViewModel.isUserLoggedIn.collectAsState()
    LaunchedEffect(key1 = isUserLoggedIn) {
        if (isUserLoggedIn) {
            navController.navigate(Home)
        } else {
            navController.navigate(Welcome)
        }
    }
    val list = listOf(
        TitleSelectedItem(
            title = "Home",
            selectedItem = ImageVector.vectorResource(R.drawable.icon_home_filled),
            unSelectedItem = ImageVector.vectorResource(R.drawable.icon_home_outlined)
        ),
        TitleSelectedItem(
            title = "Product",
            selectedItem = ImageVector.vectorResource(R.drawable.icon_shopping_bag_filled),
            unSelectedItem = ImageVector.vectorResource(R.drawable.icon_shopping_bag_outlined)
        ),
        TitleSelectedItem(
            title = "Coupon",
            selectedItem = ImageVector.vectorResource(R.drawable.icon_coupon_filled),
            unSelectedItem = ImageVector.vectorResource(R.drawable.icon_coupon_outlined)
        ),
        TitleSelectedItem(
            title = "Favorite",
            selectedItem = ImageVector.vectorResource(R.drawable.icon_favourite_filled),
            unSelectedItem = ImageVector.vectorResource(R.drawable.icon_favourite_outlined)
        ),
        TitleSelectedItem(
            title = "Profile",
            selectedItem = ImageVector.vectorResource(R.drawable.icon_profile_filled),
            unSelectedItem = ImageVector.vectorResource(R.drawable.icon_profile_outlined)
        ),

        )
    val screen = listOf(
        Home, Product, Coupon, Favorite, Profile
    )
    var isBottomBarVisible = remember { mutableStateOf(true) }
    val backStackEntry by navController.currentBackStackEntryFlow.collectAsState(null)
    val currentState = backStackEntry?.destination?.route
    val navBarState = screen.indexOfFirst { it::class.qualifiedName == currentState }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            AnimatedVisibility(visible = isBottomBarVisible.value, enter = fadeIn()) {
                NavigationBar {
                    list.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = navBarState == index,
                            onClick = {
                                val currentRoute = navController.currentDestination?.route
                                val targetRoute = screen[index]::class.qualifiedName

                                if (currentRoute != targetRoute) {
                                    navController.navigate(screen[index]) {
                                        popUpTo(navController.graph.startDestinationRoute ?: Home::class.qualifiedName!!) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }

                            
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = iconColor,
                                unselectedIconColor = MaterialTheme.colorScheme.onBackground,
                                indicatorColor = Color.Transparent // Removes background color
                            ),
                            icon = {
                                Icon(
                                    contentDescription = null,
                                    imageVector = if (navBarState == index) item.selectedItem else item.unSelectedItem,
                                    tint = if (navBarState == index) iconColor else MaterialTheme.colorScheme.onBackground
                                )
                            },
                            label = {
                                Text(
                                    item.title,
                                    color = if (navBarState == index) iconColor else MaterialTheme.colorScheme.onBackground
                                )
                            },
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(100)

                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(100)

                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(100)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Right,
                    tween(100)
                )
            },
            startDestination = if (isUserLoggedIn) Home else Welcome
        ) {
            composable<Welcome> {
                isBottomBarVisible.value = false
                WelcomeScreen(
                    navController,
                )
            }
            composable<Login> {
                isBottomBarVisible.value = false
                LoginScreen(navController)
            }
            composable<Register> {
                isBottomBarVisible.value = false

                SignUpScreen(
                    navController = navController
                )
            }
            composable<Home> {
                isBottomBarVisible.value = true
                HomeScreen(navController = navController)
            }
            composable<Product> {
                isBottomBarVisible.value = true
                ProductSearchScreen(navController)
            }
            composable<Coupon> {
                isBottomBarVisible.value = true

                CouponScreen()
            }
            composable<Favorite> {
                isBottomBarVisible.value = true

                FavoriteScreen(navHostController = navController)
            }
            composable<Profile> {
                isBottomBarVisible.value = true
                ProfileScreen(navHostController = navController)
            }
            composable<Notification> {
                isBottomBarVisible.value = false
                NotificationScreen(navController = navController)
            }

            composable<MyCart> {
                isBottomBarVisible.value = false
                CartScreen(navHostController = navController)

            }

            composable<ProductDetails> {
                isBottomBarVisible.value = false
                val args = it.toRoute<ProductDetails>()
                DetailScreen(
                    productId = args.productId,
                    navHostController = navController
                )
            }
            composable<Category> {
                isBottomBarVisible.value = false
                CategoryScreen(navController)
            }

            composable<CheckOut> {
                CheckOutScreen(
                    navHostController = navController,

                    )

            }

            composable < Success>{
                SuccessPaymentScreen(navController = navController)
            }


        }
    }
}

