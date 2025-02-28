package com.example.shopapps.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.shopapps.presentation.ui.screen.product.all_product.AllProductScreen
import com.example.shopapps.presentation.ui.screen.product.electronics.ElectronicsScreen
import com.example.shopapps.presentation.ui.screen.product.jewelery.JeweleryScreen
import com.example.shopapps.presentation.ui.screen.product.men_product.MenProductScreen
import com.example.shopapps.presentation.ui.screen.product.women_product.WomenProductScreen
import com.example.shopapps.presentation.ui.theme.primary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TabCategory(
    modifier: Modifier = Modifier,
    count: Int = 4,
    heightContent: Dp = Dp.Unspecified,
    limit: Int = 20,
    gridHeight: Dp = Dp.Unspecified,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    navigateToDetail: (Int) -> Unit
) {
    val category = listOf("All", "Men", "Women", "Electronics", "Jewelery")


    val pagerState = rememberPagerState(pageCount = { category.size })
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier) {
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.outline,
            edgePadding = 0.dp,
            indicator = {
                Box(
                    modifier = Modifier

                        .tabIndicatorOffset(it[pagerState.currentPage])
                        .fillMaxWidth()
                        .height(3.dp)
                        .padding(start = 20.dp, end = 20.dp)
                        .background(
                            color = primary,
                            shape = RoundedCornerShape(50.dp)
                        )
                )
            },
            divider = { }
        ) {
            val tabItems = category
            tabItems.forEachIndexed { index, _ ->
                Tab(
                    text = {
                        if (index == pagerState.currentPage) {
                            Text(
                                text = category[index],
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            Text(
                                text = category[index],
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.outline
                            )
                        }
                    },
                    selected = index == pagerState.currentPage,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.wrapContentSize()
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            userScrollEnabled = true,
        ) {page->
            Box(modifier = Modifier.fillMaxHeight().nestedScroll(
                rememberNestedScrollInteropConnection()
            )){
                when(page){
                    0 -> {
                        AllProductScreen(
                            gridHeight = gridHeight,
                            limit = limit,
                            height = heightContent,
                            count = count,
                            navigateToDetail = navigateToDetail,
                        )
                    }
                    1 -> {
                        MenProductScreen(
                            gridHeight = gridHeight,
                            limit = limit,
                            count = count,
                            height = heightContent,
                            navigateToDetail = navigateToDetail,
                            snackBarHostState = snackBarHostState,
                            scope = scope
                        )

                    }
                    2 -> {
                        WomenProductScreen(
                            gridHeight = gridHeight,
                            limit = limit,
                            count = count,
                            height = heightContent,
                            navigateToDetail = navigateToDetail,
                            snackBarHostState = snackBarHostState,
                            scope = scope
                        )
                    }
                    3 -> {
                        ElectronicsScreen(
                            gridHeight = gridHeight,
                            limit = limit,
                            count = count,
                            height = heightContent,
                            scope = scope,
                            navigateToDetail = navigateToDetail,
                            snackBarHostState = snackBarHostState
                        )
                    }
                    4 -> {
                        JeweleryScreen(
                            gridHeight = gridHeight,
                            limit = limit,
                            count = count,
                            height = heightContent,
                            navigateToDetail = navigateToDetail,
                            scope = scope,
                            snackBarHostState = snackBarHostState
                        )
                    }
                }
            }

        }
    }

}