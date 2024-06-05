package com.levis.nimblechallenge.presentation.ui.forgotpassword

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.Black10
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.White30
import com.levis.nimblechallenge.presentation.theme.White70
import com.levis.nimblechallenge.presentation.ui.components.CustomSnackBarMessage
import com.levis.nimblechallenge.presentation.ui.components.FilledButton
import com.levis.nimblechallenge.presentation.ui.components.MyTextField
import com.levis.nimblechallenge.presentation.ui.components.contentCustomSnackBar
import com.radusalagean.infobarcompose.InfoBar
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = hiltViewModel(),
    onGoToLogin: () -> Unit,
) {
    val context = LocalContext.current
    var snackBarMessage: CustomSnackBarMessage? by remember { mutableStateOf(null) }
    val isLoadingState = viewModel.loadingMutableStateFlow.collectAsState().value

    LaunchedEffect(viewModel.responseForgotPassword) {
        viewModel.responseForgotPassword.collectLatest {
            if (it) {
                snackBarMessage =
                    CustomSnackBarMessage(
                        title = "Check your email.",
                        textString = context.getString(R.string.success_sent_email_forgot_password)
                    )
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorMutableSharedFlow.collectLatest {
            snackBarMessage =
                CustomSnackBarMessage(
                    title = "Error.",
                    textString = it.message,
                    icon = Icons.Default.Error,
                    iconColor = Red
                )
        }
    }

    ForgotPasswordContent(
        snackBarMessage = snackBarMessage,
        isLoadingState = isLoadingState,
        onClickForgotPassword = { email ->
            viewModel.forgotPassword(email)
        },
        onGoToLogin = onGoToLogin,
        onDismissSnackBar = {
            snackBarMessage = null
        }
    )
}

@Composable
fun ForgotPasswordContent(
    snackBarMessage: CustomSnackBarMessage? = null,
    isLoadingState: Boolean = false,
    onClickForgotPassword: (String) -> Unit = {},
    onGoToLogin: () -> Unit = {},
    onDismissSnackBar: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var email by rememberSaveable { mutableStateOf("") }
    Box(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(R.drawable.bg_blur_overlay),
                    contentScale = ContentScale.FillBounds
                ),
        )
        Scaffold(
            containerColor = Color.Transparent,
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                contentAlignment = Alignment.TopCenter,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronLeft,
                        tint = Color.White,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Start)
                            .clickable {
                                onGoToLogin.invoke()
                            }
                            .testTag("btnBack")
                    )
                    Spacer(modifier = Modifier.height(54.dp))
                    Image(
                        painter = painterResource(id = R.drawable.ic_nimble_logo),
                        modifier = Modifier
                            .width(165.dp),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = stringResource(R.string.forgot_password),
                        style = MaterialTheme.typography.bodyLarge.copy(color = White70),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(60.dp))
                    InputEmailComponent(email, onTextChanged = { text -> email = text })
                    Spacer(modifier = Modifier.height(24.dp))
                    FilledButton(
                        onClick = {
                            focusManager.clearFocus()
                            onClickForgotPassword.invoke(email)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btnReset")
                    ) {
                        Text(text = stringResource(R.string.reset))
                    }
                }

                InfoBar(
                    offeredMessage = snackBarMessage,
                    content = contentCustomSnackBar,
                    onDismiss = onDismissSnackBar
                )

                if (isLoadingState) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Black10)
                            .testTag("loading"),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(50.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InputEmailComponent(
    email: String, onTextChanged: (String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    MyTextField(
        value = email,
        onValueChange = onTextChanged,
        singleLine = true,
        placeholder = {
            Text(
                text = stringResource(R.string.email),
                style = MaterialTheme.typography.bodyMedium,
                color = White30
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .testTag("emailInput"),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done, keyboardType = KeyboardType.Email, autoCorrect = false
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}

@Preview
@Composable
fun ForgotPasswordScreenPreview() {
    NimbleChallengeTheme {
        ForgotPasswordContent(onGoToLogin = {})
    }
}