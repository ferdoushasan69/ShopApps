package com.example.shopapps.presentation.ui.component

import android.widget.RatingBar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.example.shopapps.R
import com.example.shopapps.presentation.ui.theme.primary

@Composable
fun ProductCart(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
) {
    Column(
        modifier = Modifier.width(196.dp)
            .padding(16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .padding(16.dp)
                .width(196.dp)
                .height(196.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Text(
            text = title,
            maxLines = 1,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun ProductCard2(
    modifier: Modifier = Modifier,
    image: String,
    title: String,
    rating: String,
    price: String,
    category: String,
    addToCart: () -> Unit,
) {
    val ratingState by remember { mutableFloatStateOf(rating.toFloat()) }
    var filCart = rememberSaveable{ mutableStateOf(false ) }

    Column(
        modifier = modifier
            .width(160.dp)
            .padding(16.dp)
            .wrapContentHeight(),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(
                    top = 8.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
                .width(160.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(10.dp))
        )
        Column {
            Text(
                fontSize = 14.sp,
                text = category,
                color = MaterialTheme.colorScheme.outline
            )
            Text(
                text = title,
                maxLines = 1,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    fontSize = 14.sp,
                    text = "$rating/5",
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(modifier = Modifier.width(4.dp))
                RatingBar(
                    modifier = Modifier.offset(y = (-2).dp),
                    rating = ratingState,
                )
            }
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .offset(y = (5).dp)
                        .weight(1f),
                    text = "$$price",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
                Icon(
                    imageVector = if(filCart.value) Icons.Default.ShoppingCart else Icons.Filled.AddShoppingCart,
                    contentDescription = "Cart",
                    tint = MaterialTheme.colorScheme.background,
                    modifier = Modifier
                        .clickable {
                            addToCart()
                        filCart.value = true
                        }
                        .background(
                            color = primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun RatingBar(
    modifier: Modifier,
    rating: Float,
    starCount: Int = 5,
    filledStarColor: Color = Color("#FFB000".toColorInt()),
    unfilledStarColor: Color = MaterialTheme.colorScheme.outline
) {
    Row(modifier = modifier) {
        for (i in 1..starCount){
            val startFraction = rating - i + 1
            val iconTint = when{
                startFraction >= 1 -> filledStarColor
                else -> unfilledStarColor
            }
            Icon(imageVector = Icons.Default.Star,contentDescription = null, tint = iconTint, modifier = Modifier.size(15.dp))
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RatingBarPreview() {
    val rating by remember { mutableFloatStateOf(3.5f) }
    RatingBar(rating = rating, modifier = Modifier)
}