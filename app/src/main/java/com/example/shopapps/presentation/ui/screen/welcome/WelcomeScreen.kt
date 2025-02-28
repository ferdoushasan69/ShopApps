package com.example.shopapps.presentation.ui.screen.welcome

import android.annotation.SuppressLint
import android.app.AlertDialog
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.shopapps.R
import com.example.shopapps.data.local.entity.getCurrentFormattedDate
import com.example.shopapps.domain.model.Notification
import com.example.shopapps.presentation.model.getList
import com.example.shopapps.presentation.ui.navigation.Home
import com.example.shopapps.presentation.ui.navigation.Login
import com.example.shopapps.presentation.ui.navigation.Register
import com.example.shopapps.presentation.ui.theme.primary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.Cookie

@SuppressLint("CoroutineCreationDuringComposition", "Range")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var isSheetOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    val dialog by remember { mutableStateOf<AlertDialog?>(null) }
    val list = getList()
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { list.size }
    )
    val isNextVisible by remember {
        derivedStateOf {
            pagerState.currentPage != list.size - 1
        }
    }

    val isPrevVisible by remember {
        derivedStateOf {
            pagerState.currentPage != 0
        }
    }
    val modalBottomSheet = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (isSheetOpen) {
        BottomSheetRegister(
            onDismiss = {
                isSheetOpen = false
            },
            onLoginClick = {
                navController.navigate(Login)
                isSheetOpen = false
            },
            onSignUpClick = {
                navController.navigate(Register)
                isSheetOpen = false
            },
            onGuestClick = {
                scope.launch {
                    delay(1000)
                    welcomeViewModel.addNotification(
                        notification = Notification(
                            notificationType = "Info",
                            message = "Welcome to our Shop !",
                            messageDetail = "We’re thrilled to have you on board! \" +\n" +
                                    "Explore amazing deals and start shopping now.",
                            date = getCurrentFormattedDate().toLong(),
                        )
                    )

                }
                navController.popBackStack()
                navController.navigate(Home)
                isSheetOpen = false
            },
            modifier = Modifier,
            modalBottomSheetState = modalBottomSheet
        )
    }

    LaunchedEffect(Unit) {
        isVisible = true
    }


    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.7f)
            ) {
                HorizontalPager(
                    state = pagerState,
                    pageSize = PageSize.Fill,
                    beyondViewportPageCount = 1,
                    verticalAlignment = Alignment.CenterVertically,
                    key = { index -> list[index].hashCode() },
                ) { currentPage ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            list[currentPage].title,
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                        AsyncImage(
                            model = list[currentPage].img,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight(.5f)
                                .fillMaxWidth(),
                            contentScale = ContentScale.Crop
                        )
                        Text(
                            list[currentPage].desc,
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier.fillMaxWidth(.9f)
                        )
                    }
                }


            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(list.size) { index ->
                    val isSelectedPage = pagerState.currentPage == index
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .width(if (isSelectedPage) 18.dp else 8.dp)
                            .height(if (isSelectedPage) 8.dp else 8.dp)
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

            // ✅ Properly Wrapped Navigation Buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween // ✅ Ensures even spacing
            ) {
                if (isPrevVisible) {
                    Button(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    },colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    )) {
                        Text("Prev", color = Color.White)
                    }
                }

                if (isNextVisible) {
                    Button(onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1) // ✅ Corrected
                        }
                    },colors = ButtonDefaults.buttonColors(
                        containerColor = primary
                    )) {
                        Text("Next",color = Color.White)
                    }
                }
            }
            if (pagerState.currentPage == list.size - 1) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth()
                        .navigationBarsPadding()
                ) {
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                        ) + fadeIn(),
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.welcome),
                                fontWeight = FontWeight.Bold,
                                fontSize = 35.sp,
                            )

                        }
                    }
                    AnimatedVisibility(
                        visible = isVisible,
                        enter = slideInVertically(
                            initialOffsetY = { -40 },
                        ) + fadeIn(),
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(vertical = 22.dp)
                                .height(55.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            onClick = { isSheetOpen = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primary
                            )
                        ) {
                            Text(
                                text = stringResource(R.string.get_started),
                                fontSize = 18.sp,
                                color = Color.White,
                                modifier = Modifier
                            )
                        }
                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetRegister(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    modifier: Modifier = Modifier,
    modalBottomSheetState: SheetState
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) },
    ) {
        BottomSheetContent(
            modifier = modifier,
            onGuestClick = onGuestClick,
            onLoginClick = onLoginClick,
            onSignUpClick = onSignUpClick
        )
    }
}

@Composable
fun BottomSheetContent(
    modifier: Modifier = Modifier,
    onGuestClick: () -> Unit,
    onLoginClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 16.dp,
                    bottom = 32.dp
                )
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.BottomCenter)

        ) {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = onSignUpClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color =Color.White ,
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        top = 16.dp,
                        end = 16.dp
                    )
                    .height(55.dp)
                    .fillMaxWidth()
                    .border(
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(40.dp),
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Text(
                    text = stringResource(R.string.login) + " to shop Store",
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier
                        .weight(2f)
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(end = 20.dp)
                )

                Text(text = "or")

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier
                        .weight(2f)
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(start = 20.dp)
                )
            }
            Button(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp
                    )
                    .height(60.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .border(
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(40.dp),
                onClick = onGuestClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {

                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(R.drawable.icon_google),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.continue_with_google),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground ,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(0.1.dp))
                }

            }
            Button(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .height(60.dp)
                    .fillMaxWidth()
                    .border(
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(40.dp),
                onClick = onGuestClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        painter = painterResource(R.drawable.icon_facebook),
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.continue_with_facebook),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(0.1.dp))
                }
            }
            Button(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
                    .height(60.dp)
                    .fillMaxWidth()
                    .border(
                        color = MaterialTheme.colorScheme.outline,
                        width = 1.dp,
                        shape = RoundedCornerShape(10.dp)
                    ),
                shape = RoundedCornerShape(40.dp),
                onClick = onGuestClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = Color.Unspecified
                    )
                    Text(
                        text = stringResource(R.string.continue_as_guest),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onBackground ,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.width(0.1.dp))
                }

            }
        }
    }
}
