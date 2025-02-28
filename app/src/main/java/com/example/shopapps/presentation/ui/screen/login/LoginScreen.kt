package com.example.shopapps.presentation.ui.screen.login

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.domain.model.LoginResponseDomain
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.navigation.Home
import com.example.shopapps.presentation.ui.navigation.Register
import com.example.shopapps.presentation.ui.theme.primary
import com.example.shopapps.presentation.ui.theme.secondary
import com.example.shopapps.presentation.utils.DialogHelper
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val icon = if (passwordVisibility) {
        painterResource(R.drawable.icon_visibility)

    } else {
        painterResource(R.drawable.icon_visibility_off)
    }
    var dialogMsg by remember { mutableStateOf("") }
    var dialogTitle by remember { mutableStateOf("") }

    var isVisible by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    LaunchedEffect(state) {
        when (state) {
            is Resource.Error -> {
                dialogTitle = "Failed to register"
                dialogMsg = "Failed try again !"
                showDialog = true
            }

            is Resource.Loading -> {
                dialogTitle = "Loading..."
                dialogMsg = "Please wait"
            }

            is Resource.Success -> {
                showDialog = true
                dialogTitle = "Success"
                dialogMsg = "Register Successful"
                navController.navigate(Home)
            }

            null -> {}
        }
    }
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (showDialog) {
                DialogHelper.ShowDialogError(
                    onConfirm = {
                        showDialog = false
                    }
                ) { }
            }

            if (state is Resource.Loading) {

            }

            LoginContent(
                isVisible = isVisible,
                username = email,
                password = password,
                onUsernameChange = {
                    email = it
                },
                onPasswordChange = {
                    password = it
                    passwordError = password.contains(" ")
                },
                icon = icon,
                passwordVisibility = passwordVisibility,
                onPasswordVisibilityChange = {
                    passwordVisibility = !passwordVisibility
                },
                onSignUpClick = {
                    navController.navigate(Register)
                    viewModel.resetUiState()
                },
                onGuestClick = {
                    navController.navigate(Home)
                },
                onLoginClick = {
                    if (password.isEmpty() || email.isEmpty()) {
                        Toast.makeText(
                            context,
                            "Please enter email and password",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else {
                        scope.launch {

                            viewModel.login(email = email.trim(), password = password.trim()){onResult->
                                if (onResult){
                                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                    navController.navigate(Home)
                                }else{
                                    Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()

                                }
                            }

                        }
                    }
                },
                passwordError = passwordError
            )
        }

    }


}

@Composable
fun LoginContent(
    isVisible: Boolean,
    username: String,
    password: String,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    icon: Painter,
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    onLoginClick: () -> Unit,
    passwordError: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(R.string.welcome_back),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 25.sp
                )
                Text(
                    stringResource(R.string.find_your_style_no),
                    fontWeight = FontWeight.W400,
                    fontSize = 17.sp
                )
            }

        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 32.dp
                    )
            ) {
                CustomTextField(
                    value = username,
                    hint = stringResource(R.string.enter_your_username),
                    onValueChange = onUsernameChange,
                    trailingIcon = {},
                    isError = false,
                    errorMessage = ""
                )
                CustomTextField(
                    value = password,
                    hint = stringResource(R.string.enter_your_password),
                    onValueChange = onPasswordChange,
                    trailingIcon = {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            modifier = Modifier
                                .clickable {
                                    onPasswordVisibilityChange()
                                }
                        )
                    },
                    isError = passwordError,
                    errorMessage = stringResource(R.string.error_password),
                    visualTransformation = if (passwordVisibility) VisualTransformation.None
                    else PasswordVisualTransformation()
                )
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.End)
        ) {
            Text(
                text = stringResource(R.string.forgot_password),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
            )
        }

        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = secondary
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 18.sp,
                    color = Color.White,

                    modifier = Modifier
                )
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Button(
                modifier = Modifier
                    .padding(top = 32.dp)
                    .height(55.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                onClick = onLoginClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                )
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        top = 48.dp,
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

                Text(text = stringResource(R.string.or_continue_with))

                HorizontalDivider(
                    thickness = 1.dp,
                    color = Color(0xFFE3E3E3),
                    modifier = Modifier
                        .weight(2f)
                        .background(color = MaterialTheme.colorScheme.background)
                        .padding(start = 20.dp)
                )
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 32.dp,
                        vertical = 32.dp
                    ),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .border(
                            color = MaterialTheme.colorScheme.outline,
                            width = 1.dp,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.padding(),
                            painter = painterResource(R.drawable.icon_google),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .border(
                            color = MaterialTheme.colorScheme.outline,
                            width = 1.dp,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding()
                                .scale(1.3f),
                            painter = painterResource(R.drawable.icon_facebook),
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .height(60.dp)
                        .border(
                            color = MaterialTheme.colorScheme.outline,
                            width = 1.dp,
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background
                    )
                ) {
                    Row(
                        modifier = Modifier.wrapContentWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier
                                .padding()
                                .scale(1.3f)
                                .clickable {
                                    onGuestClick()
                                },
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Row(
                modifier = Modifier
                    .padding(top = 70.dp)
            ) {
                Text(
                    text = stringResource(R.string.don_t_have_an_account),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = " " + stringResource(R.string.sign_up),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable {
                            onSignUpClick()
                        }
                )
            }
        }
    }

}

@Composable
fun CustomTextField(
    value: String,
    hint: String = "",
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    isError: Boolean,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    errorMessage: String
) {
    val keyBoardController = LocalSoftwareKeyboardController.current
    TextField(
        onValueChange = onValueChange,
        value = value,
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        placeholder = {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 15.sp
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyBoardController?.hide()
        }),
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        trailingIcon = {
            trailingIcon()
        },
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 14.sp
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}
