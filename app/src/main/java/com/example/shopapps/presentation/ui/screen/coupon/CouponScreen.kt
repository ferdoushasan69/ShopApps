package com.example.shopapps.presentation.ui.screen.coupon

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapps.R
import com.example.shopapps.data.model.Coupon
import com.example.shopapps.data.model.DataDummy
import com.example.shopapps.presentation.ui.component.CouponCard2
import com.example.shopapps.presentation.ui.component.ProductCard2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CouponScreen(modifier: Modifier = Modifier) {
    val dummyCouponData  = DataDummy.dummyCoupon
    Scaffold(
        contentWindowInsets = WindowInsets(0,0,0,0),
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .statusBarsPadding(),
                title = {
                    Text(
                        text = stringResource(R.string.coupon),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) {innerPadding->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()){
            CouponContent(
                couponList = dummyCouponData
            )
        }

    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CouponContent(
    couponList : List<Coupon>
) {
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        visible = true
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
            .background(MaterialTheme.colorScheme.background)
    ) {
        items(couponList){coupon->
            AnimatedVisibility(
                modifier = Modifier.animateItemPlacement(tween(100)),
                visible = visible,
                enter = slideInVertically(
                    initialOffsetY = { -it },
                    animationSpec = tween(durationMillis = 300)
                ) + fadeIn(animationSpec = tween(durationMillis = 300))
            ) {
                CouponCard2(
                    discount = coupon.discountedPrice,
                    description = coupon.description,
                    expireDate = coupon.expiredDate,
                   couponCode = coupon.couponCode
                ) 
            }
        }
    }
}
