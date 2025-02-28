package com.example.shopapps.presentation.ui.screen.category

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.presentation.ui.component.TabCategory
import com.example.shopapps.presentation.ui.navigation.ProductDetails

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navcontroller: NavHostController) {
    val snackBrHostState = remember{ SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBrHostState){
                Snackbar(modifier = Modifier.padding(16.dp)){
                    Text(text = it.visuals.message)
                }
            }
        },
        topBar = {
            CenterAlignedTopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = {
                    Text(
                        text = stringResource(R.string.categories),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        modifier = Modifier.clickable { navcontroller.navigateUp() }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ){innerPadding->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabCategory(
                count = 10,
                gridHeight = 1000.dp,
                snackBarHostState = snackBrHostState,
                scope = scope,
                navigateToDetail = {
                    navcontroller.navigate(ProductDetails(it))
                }
            )
        }
    }
}