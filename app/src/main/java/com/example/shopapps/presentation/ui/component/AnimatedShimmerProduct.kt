package com.example.shopapps.presentation.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun AnimatedShimmerProduct() {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition()

    val transitionAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(delayMillis = 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = ""
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(
            x = transitionAnimation.value,
            y = transitionAnimation.value
        )
    )
    ShimmerGridItem(brush)
}

@Composable
fun ShimmerGridItem(brush: Brush) {
    Column(
        modifier = Modifier
            .width(160.dp)
            .wrapContentHeight()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = "",
            contentDescription = "model2",
            modifier = Modifier
                .padding(bottom = 16.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(8.dp))
                .width(160.dp)
                .background(brush)
        )

        Box(
            modifier = Modifier
                .height(10.dp) // Simulate text height
                .width(120.dp)  // Adjust width as needed
                .background(brush, RoundedCornerShape(8.dp))
        )
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .height(10.dp)
                .width(100.dp)
                .background(brush, RoundedCornerShape(8.dp))
        )

        // Empty Spacer to Prevent Text Visibility
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(50.dp)
                    .background(brush, RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .height(10.dp)
                    .width(50.dp)
                    .background(brush, RoundedCornerShape(8.dp))
            )
        }
    }
}
