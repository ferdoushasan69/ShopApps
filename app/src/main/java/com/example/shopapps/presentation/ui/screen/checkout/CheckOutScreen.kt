package com.example.shopapps.presentation.ui.screen.checkout

import AddressScreen
import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.lerp
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.shopapps.BuildConfig
import com.example.shopapps.R
import com.example.shopapps.data.model.Coupon
import com.example.shopapps.data.model.DataDummy
import com.example.shopapps.data.model.Shipping
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.domain.model.Order
import com.example.shopapps.domain.model.UserLocation
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.component.AddressItemScreen
import com.example.shopapps.presentation.ui.component.CartItemMini
import com.example.shopapps.presentation.ui.component.CouponInactiveSelected
import com.example.shopapps.presentation.ui.component.CouponItem
import com.example.shopapps.presentation.ui.component.CouponItemSelected
import com.example.shopapps.presentation.ui.component.ShippingItem
import com.example.shopapps.presentation.ui.navigation.Home
import com.stripe.android.PaymentConfiguration
import com.stripe.android.paymentsheet.PaymentSheet
import com.stripe.android.paymentsheet.PaymentSheetContract
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.PaymentSheetResultCallback
import com.stripe.android.paymentsheet.rememberPaymentSheet
import com.stripe.android.paymentsheet.rememberPaymentSheetFlowController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckOutScreen(
    navHostController: NavHostController,
    viewModel: CheckoutViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val latestOrder by viewModel.orderItems.observeAsState()
    var showDialog by remember { mutableStateOf(false) }
    var isSheetShippingOpen by remember { mutableStateOf(false) }
    var isSheetCouponOpen by remember { mutableStateOf(false) }
    val shippingItem = DataDummy.dummyShipping
    val couponItem = DataDummy.dummyCoupon
    var Username by remember { mutableStateOf("") }
    var UserAddress by remember { mutableStateOf("") }
    val selectedCouponById by viewModel.selectedCouponId.observeAsState()
    val selectedShippingId by viewModel.selectedShippingId.observeAsState()
    var addressDialog by remember { mutableStateOf(false) }
    val subTotalPrice = latestOrder?.totalPrice ?: 0.0
    val scope = rememberCoroutineScope()
    val shippingCost = selectedShippingId?.let {
        shippingItem[it].price
    } ?: 0.0

    val selectedCoupon = selectedCouponById?.let { couponItem[it] }

    val finalPrice = selectedCoupon?.let {
        calculateFinalPrice(
            subTotalPrice = subTotalPrice, shippingPrice = shippingCost, coupon = it
        )
    }





        Spacer(modifier = Modifier.height(16.dp))

    if (isSheetShippingOpen) {
        BottomSheetShippingOpen(onDismiss = {
            isSheetShippingOpen = false
        },
            shippingList = shippingItem,
            selectedShippingId = selectedShippingId,
            onChose = { id ->
                viewModel.selectShipping(id = id)
            },
            onConfirmShipping = {
                isSheetShippingOpen = false
            })
    }

    if (isSheetCouponOpen) {
        BottomSheetCouponOpen(onDismiss = {
            isSheetCouponOpen = false
        }, couponList = couponItem, selectedCouponId = selectedCouponById, onChoose = { id ->
            viewModel.selectedCouponId(id)
        }, onConfirmationCoupon = {
            isSheetCouponOpen = false
        })
    }



    var isLoading by remember { mutableStateOf(false) }



    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .padding(horizontal = 16.dp)
            .statusBarsPadding(),
            title = {
                Text(
                    text = stringResource(R.string.checkout),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
            },
            navigationIcon = {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "back",
                    modifier = Modifier.clickable {
                            navHostController.navigateUp()
                        })
            },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.background
            )
        )
    }) { innerPadding ->
            CheckOutContent(
                modifier = Modifier.padding(innerPadding),
                onEditAddress = {
                    addressDialog = true
                },
                onOrder = latestOrder,
                onShowDialog = {
                    showDialog = true
                },
                onChoosingShipping = {
                    isSheetShippingOpen = true
                },
                onChoosingCoupon = {
                    isSheetCouponOpen = true
                },
                selectedShippingId = selectedShippingId,
                selectedCouponId = selectedCouponById,
                shippingList = shippingItem,
                couponList = couponItem,
                isButtonEnabled = true,
                onChoosePayment = {
                    scope.launch {
                        delay(2000)
                        navHostController.navigate(Home)
                        isLoading = true
                    }
                    viewModel.addNotification(
                        message = "Your Order Has been placed",
                        notificationType = "Shopping",
                        firstProductImage = latestOrder?.items?.firstOrNull()?.productImage ?: "",
                        firstProductName = latestOrder?.items?.firstOrNull()?.productName ?: "",
                        quantityCheckOut = latestOrder?.items?.size ?: 0
                    )
                    if (selectedCouponById != null) {
                        viewModel.addCheckOut(
                            receiverName = "Recive name",
                            receiverAddress = "Recive Address",
                            orderItems = latestOrder?.items ?: emptyList(),
                            shippingMethod = shippingItem[selectedShippingId!!].name,
                            shippingCost = shippingCost,
                            shippingDescription = shippingItem[selectedShippingId ?: 0].description,
                            paymentMethod = "Cash on Delivery",
                            coupon = couponItem[selectedCouponById!!].discountedPrice,
                            totalPrice = finalPrice ?: 0.0
                        )
                    } else {
                        viewModel.addCheckOut(
                            receiverName = "Recive name",
                            receiverAddress = "Recive address",
                            orderItems = latestOrder?.items ?: emptyList(),
                            shippingMethod = shippingItem[selectedShippingId ?: 0].name,
                            shippingCost = shippingCost,
                            shippingDescription = shippingItem[selectedShippingId ?: 0].description,
                            paymentMethod = "Cash on Delivery",
                            coupon = couponItem[selectedShippingId ?: 0].discountedPrice,
                            totalPrice = finalPrice ?: 0.0
                        )
                    }
                    Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()

                },
                finalPrice = finalPrice ?: 0.0,
                showAddressDialog = {
                    addressDialog = it
                },
                username = Username,
                userAddress = UserAddress,
                updateData = { name, address ->
                    Username = name
                    UserAddress = address
                },
                viewModel = viewModel,
                navHostController = navHostController,
            )
        }
        if (showDialog) {
            CartItemDialog(items = latestOrder?.items ?: emptyList(),
                onDismiss = { showDialog = false })
        }
    if (isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
            CircularProgressIndicator()
        }
    }
    if(addressDialog){
        AlertDialog(
            onDismissRequest = {
                addressDialog =false
            },
            content = {
                AddressScreen(
                    onConfirm = {name,address->
                        Username = name
                        UserAddress = address
                        Log.d(TAG, "CheckOutScreen: $Username address is $UserAddress")
                    },
                    disMissDialog = {bol->
                        addressDialog = bol
                    }
                )
            }
        )
    }
    }



@Composable
fun CartItemDialog(
    items: List<Cart>, onDismiss: () -> Unit
) {
    AlertDialog(modifier = Modifier.wrapContentHeight(),
        shape = RoundedCornerShape(10.dp),
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = { onDismiss() },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Your Order", fontWeight = FontWeight.Bold, fontSize = 18.sp
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier.height(364.dp)
            ) {
                items(items) {
                    CartItemMini(productName = it.productName,
                        price = it.productPrice,
                        orderCount = it.productQuantity,
                        image = it.productImage,
                        totalOrder = 0,
                        onDetailOrder = {})
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = "OK", fontWeight = FontWeight.SemiBold
                )
            }
        })
}
    @Composable
    fun CheckOutContent(
        modifier: Modifier = Modifier,
        onEditAddress: () -> Unit,
        onOrder: Order? = null,
        username : String,
        userAddress : String,
        navHostController: NavHostController,
        onShowDialog: () -> Unit,
        showAddressDialog : (Boolean)->Unit,
        onChoosingShipping: () -> Unit,
        onChoosingCoupon: () -> Unit,
        selectedShippingId: Int?,
        selectedCouponId: Int?,
        shippingList: List<Shipping>,
        couponList: List<Coupon>,
        isButtonEnabled: Boolean = false,
        onChoosePayment: () -> Unit,
        finalPrice: Double,
        updateData:(String,String)->Unit,
        viewModel: CheckoutViewModel
    ) {

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(MaterialTheme.colorScheme.background)
                    .weight(1f)
            ) {
                if (username.isEmpty() && userAddress.isEmpty()){
                    OutlinedButton(onClick = {
                        showAddressDialog(true)

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {
                        Text("Choose address")
                    }
                }else{
                    Card(border = BorderStroke(width = 1.dp, color = Color.LightGray),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                showAddressDialog(true)
                                updateData(username, userAddress)
                            }
                            .height(125.dp)) {
                        AddressItemScreen(
                            name = username, address = userAddress
                        )
                    }
                }


                onOrder?.items?.firstOrNull()?.let { cart ->
                    CartItemMini(productName = cart.productName,
                        image = cart.productImage,
                        price = cart.productPrice,
                        orderCount = cart.productQuantity,
                        totalOrder = onOrder.items.size,
                        onDetailOrder = {
                            onShowDialog()
                        })
                }

                if (selectedShippingId == null) {
                    Card(border = BorderStroke(
                        width = 1.dp, color = Color.LightGray
                    ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                onChoosingShipping()
                            }) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Choose Shipping",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.weight(1f)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Edit",
                                    fontSize = 14.sp,
                                )
                                Spacer(modifier = Modifier.size(4.dp))
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                                    contentDescription = "",
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                } else {
                    val selectedShipping = shippingList[selectedShippingId]
                    Card(border = BorderStroke(
                        width = 1.dp, color = Color.LightGray
                    ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .clickable {
                                onChoosingShipping()
                            }) {
                        ShippingItem(
                            isChoose = true,
                            onChoose = { onChoosingShipping() },
                            name = selectedShipping.name,
                            price = selectedShipping.price,
                            description = selectedShipping.description
                        )
                    }
                }

                if (selectedCouponId == null) {
                    CouponInactiveSelected(modifier = Modifier.clickable {
                        onChoosingCoupon()
                    })
                } else {
                    val selectedCoupon = couponList[selectedCouponId]
                    CouponItemSelected(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .clickable { onChoosingCoupon() },
                        discountTittle = selectedCoupon.discountedPrice
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column(
                    modifier = Modifier.padding(
                        top = 16.dp, start = 16.dp, end = 16.dp
                    )
                ) {
                    Text(
                        text = "Payment Summary", fontWeight = FontWeight.SemiBold
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Sub-total",
                            color = MaterialTheme.colorScheme.outline
                        )
                        Text(
                            text = "$${"%.2f".format(onOrder?.totalPrice)}",
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Shipping Charge",
                            color = MaterialTheme.colorScheme.outline
                        )
                        selectedShippingId?.let {
                            Text(
                                text = "$${shippingList[selectedShippingId].price}",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } ?: run {
                            Text(
                                text = "$0.00",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Final Price",
                            color = MaterialTheme.colorScheme.outline
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (selectedCouponId != null) {
                                Text(
                                    text = if (selectedShippingId == null) "$${"%.2f".format(onOrder?.totalPrice)}"
                                    else "$${
                                        "%.2f".format(
                                            (onOrder?.totalPrice)?.plus(shippingList[selectedShippingId].price)
                                        )
                                    }",
                                    textDecoration = TextDecoration.LineThrough,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$${"%.2f".format(finalPrice)}",
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                    Button(
                        enabled = isButtonEnabled,
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 8.dp)
                            .height(55.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = onChoosePayment,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text(
                            text = "Choose Payment", fontSize = 16.sp, color = Color.White
                        )
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheetCouponOpen(
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit,
        couponList: List<Coupon>,
        selectedCouponId: Int?,
        onChoose: (Int) -> Unit,
        onConfirmationCoupon: () -> Unit
    ) {

        ModalBottomSheet(onDismissRequest = onDismiss,
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = { WindowInsets(0, 0, 0, 0) }) {
            BottomSheetCouponContent(
                modifier = Modifier
                    .navigationBarsPadding()
                    .wrapContentHeight(),
                state = couponList,
                selectedCouponId = selectedCouponId,
                onChoose = onChoose,
                onConfirmationCoupon = onConfirmationCoupon
            )
        }
    }

    @Composable
    fun BottomSheetCouponContent(
        modifier: Modifier = Modifier,
        state: List<Coupon>,
        selectedCouponId: Int?,
        onChoose: (Int) -> Unit,
        onConfirmationCoupon: () -> Unit
    ) {
        var temporarySelectedCouponId by rememberSaveable { mutableStateOf(selectedCouponId) }

        Box(
            modifier = modifier.background(
                    color = MaterialTheme.colorScheme.background
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp, end = 16.dp, bottom = 8.dp
                    )
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
            ) {
                LazyColumn {
                    itemsIndexed(items = state) { index, coupon ->
                        CouponItem(
                            isChoose = temporarySelectedCouponId == index,
                            onChoose = { temporarySelectedCouponId = index },
                            discount = coupon.discountedPrice,
                            description = coupon.description,
                            color1 = coupon.color1,
                            color2 = coupon.color2,
                            modifier = modifier,
                            expireDate = coupon.expiredDate,
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(55.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onChoose(temporarySelectedCouponId?:0)
                        onConfirmationCoupon()
                        
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Confirmation", fontSize = 14.sp, color = Color.White
                    )
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BottomSheetShippingOpen(
        modifier: Modifier = Modifier,
        onDismiss: () -> Unit,
        shippingList: List<Shipping>,
        selectedShippingId: Int?,
        onChose: (Int) -> Unit,
        onConfirmShipping: () -> Unit
    ) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
            contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
        ) {
            BottomSheetShippingContent(
                modifier = Modifier
                    .navigationBarsPadding()
                    .wrapContentHeight(),
                selectedShippingId = selectedShippingId,
                onChose = onChose,
                onConfirmShipping = onConfirmShipping,
                shippingList = shippingList
            )
        }
    }

    @Composable
    fun BottomSheetShippingContent(
        modifier: Modifier = Modifier,
        shippingList: List<Shipping>,
        selectedShippingId: Int?,
        onChose: (Int) -> Unit,
        onConfirmShipping: () -> Unit,
    ) {

        var temporaryCouponId by rememberSaveable { mutableStateOf(selectedShippingId) }
        Box(
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .align(Alignment.BottomCenter)
            ) {
                LazyColumn {
                    itemsIndexed(shippingList) { index, item ->
                        ShippingItem(
                            modifier = Modifier,
                            onChoose = { temporaryCouponId = index },
                            isChoose = temporaryCouponId == index,
                            description = item.description,
                            name = item.name,
                            price = item.price
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .height(55.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        onChose(temporaryCouponId?:0)
                        onConfirmShipping()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text("Confirmation", fontSize = 14.sp, color = Color.White)

                }
            }

        }
    }

    fun calculateFinalPrice(
        subTotalPrice: Double, shippingPrice: Double?, coupon: Coupon

    ): Double {
        var finalPrice = subTotalPrice
        val shippingCost = if (coupon.discountedPrice == "FREE SHIPPING") {
            0.0
        } else {
            shippingPrice ?: 0.0
        }

        finalPrice += shippingCost

        coupon.discountedPrice.let { disCount ->
            if (disCount.endsWith("%")) {
                val discountPercentage = disCount.replace("%", "").toDoubleOrNull() ?: 0.0
                finalPrice -= (finalPrice * (discountPercentage / 100))
            }
        }
        return finalPrice
    }

