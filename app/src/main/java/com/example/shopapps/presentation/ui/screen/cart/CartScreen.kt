package com.example.shopapps.presentation.ui.screen.cart

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.presentation.ui.component.CartItem
import com.example.shopapps.presentation.ui.navigation.CheckOut
import com.example.shopapps.presentation.ui.navigation.ProductDetails
import com.example.shopapps.presentation.ui.theme.primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    navHostController: NavHostController,
    viewModel: CartViewModel = hiltViewModel()
) {

    val cartItems by viewModel.cartItems.observeAsState(emptyList())
    val totalPrice by viewModel.totalPrice.observeAsState(0.0)
    val checkedSingleValue by remember { mutableStateOf(false) }
    var checkedAllValue by remember { mutableStateOf(false) }
    val selectedCartItems by viewModel.selectedCartItems.observeAsState(emptySet())
    val isSelectedEnabled = selectedCartItems.isNotEmpty()

    LaunchedEffect(cartItems.size, selectedCartItems.size) {
        checkedAllValue = if (cartItems.isNotEmpty()) {
            selectedCartItems.size == cartItems.size
        } else {
            false
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.my_cart),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier
                            .clickable {
                                navHostController.navigateUp()
                            }
                    )
                },

                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (cartItems.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .align(Alignment.Center)
                ) {
                    Text("Cart is Empty", color = MaterialTheme.colorScheme.outline)
                    Text("C'mon add some items", color = MaterialTheme.colorScheme.outline)
                }
            } else {
                CartContent(
                    navHostController = navHostController,
                    cartList = cartItems,
                    onQuantityChange = {cart,quantity->
                        viewModel.updateQuantity(cart,quantity)
                    },
                    totalPrice = totalPrice,
                    selectedCartItems = selectedCartItems,
                    checkedAllValue = checkedAllValue,
                    onCheckedChange = {cart, b ->  
                        viewModel.updateCheckedItem(cart,b)
                    },
                    onCheckedAllChange = {isChecked->
                        cartItems.forEach { item->
                            viewModel.updateCheckedItem(item,isChecked)
                        }
                    },
                    addOrder = {
                        viewModel.createOrderFromSelectedItems()
                        navHostController.navigate(CheckOut)
                    },
                    isCheckoutEnabled = isSelectedEnabled
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CartContent(
    navHostController: NavHostController,
    cartList : List<Cart>,
    onQuantityChange: (Cart, Int) -> Unit,
    modifier: Modifier = Modifier,
    totalPrice: Double,
    selectedCartItems: Set<Cart>,
    checkedAllValue: Boolean,
    onCheckedChange: (Cart, Boolean) -> Unit,
    onCheckedAllChange : (Boolean)->Unit,
    addOrder: () -> Unit,
    isCheckoutEnabled: Boolean = false
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        var visible by remember { mutableStateOf(false) }
        LaunchedEffect(Unit) {
            visible = true
        }
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.background)
        ) {
            items(cartList, key = {it.id}){cartItem->
                AnimatedVisibility(modifier = Modifier.animateItem(
                    fadeInSpec = null,
                    fadeOutSpec = null,
                    placementSpec = tween(100)
                ), visible = visible, enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))) {
                    CartItem(
                        modifier = Modifier.clickable {
                            navHostController.navigate(ProductDetails(cartItem.productId))
                        },
                        imageId = cartItem.productImage,
                        productName = cartItem.productName,
                        category = cartItem.productCategory,
                        price = cartItem.productPrice,
                        orderCount = cartItem.productQuantity,
                        checkedValue = checkedAllValue || cartItem.id in selectedCartItems.map { it.id },
                        quantityChange = {newQuantity->
                            onQuantityChange(cartItem,newQuantity)
                        },
                        onCheckChange = {isChecked ->
                            onCheckedChange(cartItem,isChecked)
                        }
                    )

                }


            }
        }


        Box(modifier = Modifier.fillMaxWidth()
            .height(85.dp)
            .background(MaterialTheme.colorScheme.background)
        ){
            HorizontalDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .fillMaxWidth()
            ) {
                Checkbox(
                    checked = checkedAllValue,
                    onCheckedChange = onCheckedAllChange,
                )
                Text("Select all",modifier = Modifier.padding(end = 32.dp),)
                Column(modifier = Modifier.weight(1f)){
                    Spacer(Modifier.height(6.dp))
                    Text(
                        fontWeight = FontWeight.SemiBold,
                        text = "Total",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        text = "\$${"%.2f".format(totalPrice)}"
                    )
                }
                Spacer(modifier = Modifier.size(16.dp))
                Button(
                    enabled = isCheckoutEnabled,
                    modifier = Modifier
                        .height(55.dp)
                        .width(120.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = addOrder,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.checkout),
                        fontSize = 14.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
