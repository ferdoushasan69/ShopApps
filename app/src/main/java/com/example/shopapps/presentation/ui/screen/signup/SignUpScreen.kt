package com.example.shopapps.presentation.ui.screen.signup

import android.app.AlertDialog
import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.shopapps.R
import com.example.shopapps.presentation.ui.common.Resource
import com.example.shopapps.presentation.ui.navigation.Home
import com.example.shopapps.presentation.ui.navigation.Login
import com.example.shopapps.presentation.ui.navigation.Register
import com.example.shopapps.presentation.ui.theme.ShopAppsTheme
import com.example.shopapps.presentation.ui.theme.primary
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel= hiltViewModel(),
    navController: NavHostController
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    var passwordVisibility by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var checked by remember { mutableStateOf(false) }
    var email by rememberSaveable { mutableStateOf("") }
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var isVisible by remember { mutableStateOf(false) }
    var dialog by remember { mutableStateOf<AlertDialog?>(null) }
    val icon = if (passwordVisibility)
        painterResource(R.drawable.icon_visibility)
    else
        painterResource(R.drawable.icon_visibility_off)


    val state by signUpViewModel.state.collectAsState()

        var showDialog by remember { mutableStateOf(false) }
        var dialogMessage by remember { mutableStateOf("") }
        var dialogTitle by remember { mutableStateOf("") }

        LaunchedEffect(state) {
            when (state) {
                is Resource.Loading -> {
                    dialogTitle = "Loading"
                    dialogMessage = "Please wait..."
                    showDialog = true
                }
                is Resource.Success -> {
                    dialogTitle = "Success"
                    dialogMessage = "Register Success"
                    showDialog = true
                    signUpViewModel.resetUiState()
                    navController.navigate(Home)
                }
                is Resource.Error -> {
                    dialogTitle = "Failed"
                    dialogMessage = (state as Resource.Error).errorMsg
                    showDialog = true
                }
                else -> {}
            }
        }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    SignUpContent(
        email = email,
        username = username,
        password = password,
        checked = checked,
        onEmailChange = {
            email = it
            emailError = !Patterns
                .EMAIL_ADDRESS
                .matcher(email)
                .matches()
        },
        onUsernameChange = { username = it },
        onPasswordChange = {
            password = it
            passwordError = password.contains(" ")
       },
        icon = icon,
        passwordVisibility = passwordVisibility,
        onPasswordVisibilityChange = { passwordVisibility = !passwordVisibility },
        onCheckedChange = { checked = it },
        onSignUpClick = {
            if (
                email.isEmpty() ||
                password.isEmpty()
                ) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }else if(password.length <= 6){
                Toast.makeText(context, "Password must be at least 6 character ", Toast.LENGTH_SHORT).show()
            }
            else {
                scope.launch {
                    signUpViewModel.register(
                        password = password.trim(),
                        email = email.trim(),
                    ){onResult->
                        if (onResult){
                            Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()

                            navController.navigate(Home)
                        }else{
                            Toast.makeText(context, "Register failed", Toast.LENGTH_SHORT).show()

                        }
                    }
                }
            }
        },
        onLoginClick = {
            navController.navigate(Login)
        },
        passwordError = passwordError,
        emailError = emailError,
        isVisible = isVisible
    )

}

@Composable
fun SignUpContent(
    email: String,
    username: String,
    password: String,
    checked: Boolean,
    onEmailChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    icon: Painter,
    passwordVisibility: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    onCheckedChange: (Boolean) -> Unit,
    onSignUpClick: () -> Unit,
    onLoginClick: () -> Unit,
    passwordError: Boolean,
    emailError: Boolean,
    isVisible: Boolean,
) {
    Column(
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(
                top = 100.dp,
                start = 32.dp,
                end = 32.dp
            )
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = slideInVertically(
                initialOffsetY = { -40 },
                animationSpec = tween(1000)
            ) + fadeIn(animationSpec = tween(1000)),
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
                        .size(40.dp)
                        .offset(y = (-9).dp)
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
            Text(
                text = stringResource(R.string.intro_sign_up),
                fontWeight = FontWeight.Light,
                fontSize = 26.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
            )
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
                CustomOutlinedTextField(
                    value = email,
                    hint = stringResource(R.string.enter_your_email),
                    onValueChange = onEmailChange,
                    trailingIcon = {},
                    isError = emailError,
                    errorMessage = stringResource(R.string.error_email)
                )
                CustomOutlinedTextField(
                    value = username,
                    hint = stringResource(R.string.enter_your_username),
                    onValueChange = onUsernameChange,
                    trailingIcon = {},
                    isError = false,
                    errorMessage = null
                )
                CustomOutlinedTextField(
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
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Start)
            ) {
                RoundedCornerCheckbox(
                    label = stringResource(R.string.term_and_condition),
                    isChecked = checked,
                    onValueChange = onCheckedChange,
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
                onClick = onSignUpClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = primary
                )
            ) {
                Text(
                    text = stringResource(R.string.sign_up),
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
                modifier = Modifier
                    .padding(top = 32.dp)
            ) {
                Text(
                    text = stringResource(R.string.already_have_an_account),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = " "+ stringResource(R.string.login),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .clickable {
                            onLoginClick()
                        }
                )
            }
        }
    }
}


@Composable
fun RoundedCornerCheckbox(
    label: String,
    isChecked: Boolean,
    modifier: Modifier = Modifier,
    size: Float = 20f,
    checkedColor: Color = MaterialTheme.colorScheme.primary,
    uncheckedColor: Color = MaterialTheme.colorScheme.background,
    onValueChange: (Boolean) -> Unit
) {
    val checkboxColor: Color by animateColorAsState(if (isChecked) checkedColor else uncheckedColor,
        label = ""
    )
    val density = LocalDensity.current
    val duration = 200

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .heightIn(48.dp)
            .toggleable(
                value = isChecked,
                role = Role.Checkbox,
                onValueChange = onValueChange
            )
    ) {
        Box(
            modifier = Modifier
                .size(size.dp)
                .background(
                    color = checkboxColor,
                    shape = RoundedCornerShape(4.dp)
                )
                .border(
                    width = 1.5.dp,
                    color = checkedColor,
                    shape = RoundedCornerShape(4.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isChecked,
                enter = slideInHorizontally(animationSpec = tween(duration)) {
                    with(density) { (size * -0.5).dp.roundToPx() }
                } + expandHorizontally(
                    expandFrom = Alignment.Start,
                    animationSpec = tween(duration)
                ),
                exit = fadeOut()
            ) {
              Icon(
                  imageVector = Icons.Default.Check ,
                  tint = checkboxColor,
                  contentDescription = null,
              )
            }
        }
        Text(
            modifier = Modifier
                .padding(start = 16.dp),
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Light,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    trailingIcon: @Composable () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean,
    errorMessage: String?,
    hint: String = ""
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        shape = RoundedCornerShape(32.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor =Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent

        ),
        value = value,
        onValueChange = onValueChange,
        placeholder =  {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.outline,
                fontSize = 15.sp,
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
        }),
        modifier = modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        trailingIcon = {
            trailingIcon()
        },
        visualTransformation = visualTransformation,
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 14.sp,
        ),
        isError = isError,
        supportingText = {
            if (isError) {
                if (errorMessage != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = errorMessage,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        },
    )
}

@Preview(
    showBackground = true,
)
@Composable
private fun SignUpContentPreview() {
    ShopAppsTheme {
        SignUpContent(
            email = "",
            username = "",
            password = "",
            checked = true,
            onEmailChange = {},
            onUsernameChange = {},
            onPasswordChange = {},
            icon = painterResource(R.drawable.icon_visibility_off),
            passwordVisibility = false,
            onPasswordVisibilityChange = {},
            onSignUpClick = {},
            onCheckedChange = {},
            onLoginClick = {},
            passwordError = true,
            emailError = true,
            isVisible = true
        )
    }
}