package com.example.shopapps.presentation.ui.screen.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.domain.model.Cart
import com.example.shopapps.presentation.ui.navigation.MyCart
import com.example.shopapps.presentation.ui.navigation.Welcome

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    navHostController: NavHostController,
) {
    var showDialog = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val getUserName by viewModel.getUserName().observeAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Your Profile",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_profile_filled),
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(100.dp))
                        .border(
                            width = 2.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        ),
                    tint = MaterialTheme.colorScheme.outline
                )
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .weight(1f)
                ) {
                    Text(
                        letterSpacing = 0.001.sp,
                        text = "Ashes",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black,
                        fontSize = 20.sp,

                        )
                    Text(
                        text = "$ 2.341.000",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(
                color = Color(0xFFE3E3E3),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        navHostController.navigate(MyCart)
                    }
            ) {
                Icon(
                    Icons.Default.ShoppingBag,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    "My Order", fontSize = 20.sp, fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f).padding(start = 10.dp)
                )
                Icon(
                    Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp,)
                    .fillMaxWidth()
                    .clickable {
                        showDialog.value = true
                    },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Logout,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    "Logout",
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    if (showDialog.value){
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = { Text("Logout Confirmation") },
            text = { Text("Are you sure you want to log out?") },
            confirmButton = {
                Text(
                    "Yes",
                    modifier = Modifier.clickable {
                        showDialog.value = false
                        viewModel.logout()
                        navHostController.navigate(Welcome)
                        Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                    }
                )
            },
            dismissButton = {
                Text(
                    "No",
                    modifier = Modifier.clickable {
                        showDialog.value = false
                    }
                )
            }
        )
    }
}