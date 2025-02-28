package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.shopapps.data.model.dummyImage
import kotlinx.coroutines.delay

@Composable
fun ImageSlider() {
    val pagerState = rememberPagerState(pageCount = { dummyImage.size })
    Column {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp),
            contentPadding = PaddingValues(horizontal = 10.dp),
            pageSpacing = 16.dp,
        ) { page ->
            Image(
                painter = painterResource(dummyImage[page].image), 
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                   .height(250.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            repeat(dummyImage.size) { index ->
                val isSelectedPage = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .width(if (isSelectedPage) 18.dp else 12.dp)
                        .height(if (isSelectedPage) 12.dp else 12.dp)
                        .border(
                            width = 1.dp, color = Color(0xFF707784),
                            shape = RoundedCornerShape(10.dp)
                        )
                        .background(
                            color = if (isSelectedPage) Color(0xFF3B6C64)
                            else Color(0xFFFFFFFF), shape = CircleShape
                        )
                )


            }
        }
        LaunchedEffect(Unit) {
            while (true) {
                delay(4000)
                val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                pagerState.animateScrollToPage(nextPage)
            }
        }

    }
}