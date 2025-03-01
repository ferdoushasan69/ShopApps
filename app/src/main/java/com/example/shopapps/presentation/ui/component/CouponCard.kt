package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shopapps.presentation.ui.theme.primary

@Composable
fun CouponCard(
    modifier: Modifier = Modifier,
    discount: String,
    description: String,
    expireDate: String,
    color1: Color,
    fontSizedDiscount: Int = 40,
    fontSizedExpireDate: Int = 14,
    fontSizeDescription: Int = 14,
    height: Dp = 170.dp,
    color2: Color
) {
    val gradient = Brush.linearGradient(
        colors = listOf(
            color1,
            color2
        )
    )

    Card(
        modifier = modifier
            .height(height)
            .fillMaxWidth(),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(primary)
        ) {
            Text(
                text = discount,
                fontSize = fontSizedDiscount.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier
                    .padding(16.dp)
            )
            Column(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
                    .align(Alignment.BottomStart)
            ) {
                Text(
                    text = description,
                    fontWeight = FontWeight.Medium,
                    color = Color.White,
                    fontSize = fontSizeDescription.sp
                )
                Text(
                    text = "Valid until $expireDate",
                    fontWeight = FontWeight.Light,
                    color = Color.White,
                    fontSize = fontSizedExpireDate.sp
                )
            }
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = (-10).dp)
            )
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.background,
                modifier = Modifier
                    .size(20.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = 10.dp)
            )


        }
    }

}

@Composable
fun CouponCard2(
    modifier: Modifier = Modifier,
    discount: String,
    description: String,
    expireDate: String,
    couponCode: String
) {

    Card(
        elevation = CardDefaults.elevatedCardElevation(
            focusedElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(80.dp)
                    .background(primary)
                    .align(Alignment.CenterStart),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = discount,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (discount == "FREE SHIPPING") 16.sp else 30.sp,
                    modifier = Modifier
                        .rotate(270f)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(
                        start = 90.dp,
                        end = 16.dp,
                        top = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Text(text = couponCode, color = Color.Black, fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    color = Color(0xFF4CAF50),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = expireDate,
                    color = Color.Gray,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp
                )
            }
        }
    }
}