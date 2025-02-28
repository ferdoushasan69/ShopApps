package com.example.shopapps.presentation.ui.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun AnimatedShimmerDetailsProduct(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )

    val transition = rememberInfiniteTransition(label = "")
    val transitionAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            ),
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

    ShimmerItem(
        brush = brush,
        modifier = modifier
    )
}

@Composable
fun ShimmerItem(brush: Brush, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = "",
                contentDescription = null,
                modifier = Modifier
                    .background(brush)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .height(300.dp)

            )
            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(22.dp)
                    .fillMaxWidth(.7f),

                )

            Box(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(20.dp)
                    .fillMaxWidth(.5f),

                )

            Box(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .background(
                        brush = brush,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .height(18.dp)
                    .fillMaxWidth(.9f)
            )
        }

        Box(
            modifier = Modifier
                .background(brush, RoundedCornerShape(8.dp))
                .height(14.dp)
                .fillMaxWidth(1f)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .height(85.dp)
                .background(Color.White)
        ) {
            HorizontalDivider()
            Row (
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.align(Alignment.CenterStart)
                    .padding(16.dp)
                    .fillMaxWidth()
            ){
                Box(
                    modifier = Modifier
                        .background(brush, RoundedCornerShape(8.dp))
                        .height(40.dp)
                        .fillMaxWidth(0.3f)
                )
                Spacer(modifier = Modifier.weight(1f))
                Surface(
                    modifier = Modifier
                        .height(55.dp)
                        .width(170.dp)
                        .clip(RoundedCornerShape(8.dp)),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(brush)
                    )
                }
            }
        }
    }
}
