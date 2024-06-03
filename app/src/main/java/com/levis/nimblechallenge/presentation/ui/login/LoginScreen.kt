package com.levis.nimblechallenge.presentation.ui.login

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.Black10
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.White30
import com.levis.nimblechallenge.presentation.theme.White50
import com.levis.nimblechallenge.presentation.ui.components.FilledButton
import com.levis.nimblechallenge.presentation.ui.components.MyTextField
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.collectLatest

const val TIME_DELAY_DISPLAY_LOGO = 1000
const val ANIMATION_TIME_DURATION = 600

@Composable
fun LoginScreen(
    onGoToHome: () -> Unit,
    onGoToForgotPassword: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isLoadingState = viewModel.loadingMutableStateFlow.collectAsState().value

    LaunchedEffect(viewModel.navEvent) {
        viewModel.navEvent.collectLatest {
            when (it) {
                LoginNavEvent.Home -> onGoToHome.invoke()
                LoginNavEvent.ForgotPassword -> onGoToForgotPassword.invoke()
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorMutableSharedFlow.collectLatest {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    LoginContent(
        isLoadingState = isLoadingState,
        onClickedLogin = { email, password ->
            viewModel.login(email, password)
        },
        onGoToForgotPassword = onGoToForgotPassword
    )

}

@Composable
fun LoginContent(
    isLoadingState: Boolean = false,
    onClickedLogin: (String, String) -> Unit,
    onGoToForgotPassword: () -> Unit
) {

    var bgWindow by rememberSaveable { mutableIntStateOf(R.drawable.bg_window) }
    LaunchedEffect(key1 = bgWindow) {
        bgWindow = R.drawable.bg_blur_overlay
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            bgWindow, animationSpec = tween(
                ANIMATION_TIME_DURATION,
                delayMillis = TIME_DELAY_DISPLAY_LOGO,
                easing = LinearEasing
            ), label = "Ani change background image"
        ) { targetState ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(targetState), contentScale = ContentScale.FillBounds
                    ),
            )
        }
        ImageLogoComponent()
        LoginInputContent(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(24.dp),
            onClickedLogin = onClickedLogin,
            onGoToForgotPassword = onGoToForgotPassword
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

@Composable
fun ImageLogoComponent(modifier: Modifier = Modifier) {
    // Make animation of this image from center height to top
    val startPosition = Offset(0f, 0f)
    val endPosition = Offset(0f, -200f)
    val animatedPosition by remember {
        mutableStateOf(
            Animatable(
                startPosition, Offset.VectorConverter
            )
        )
    }
    val animatedSizeOffset by rememberSaveable { mutableStateOf(Animatable(0f)) }
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = visible) {
        Log.d("trunglx--current thread", "Current thread " + Thread.currentThread().toString())
        visible = true
    }
    LaunchedEffect(key1 = animatedPosition, key2 = animatedSizeOffset) {
        awaitAll(async {
            animatedPosition.animateTo(targetValue = endPosition, animationSpec = keyframes {
                durationMillis = ANIMATION_TIME_DURATION
                delayMillis = TIME_DELAY_DISPLAY_LOGO
            })
        }, async {
            animatedSizeOffset.animateTo(targetValue = -35f, animationSpec = keyframes {
                durationMillis = ANIMATION_TIME_DURATION
                delayMillis = TIME_DELAY_DISPLAY_LOGO
            })
        })
    }
    AnimatedVisibility(
        visible, modifier = modifier, enter = fadeIn(animationSpec = tween(ANIMATION_TIME_DURATION))
    ) {
        Box(
            modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_nimble_logo),
                modifier = Modifier
                    .width(animatedSizeOffset.value.dp + 200.dp)
                    .offset(y = animatedPosition.value.y.dp),
                contentDescription = null,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
fun LoginInputContent(
    modifier: Modifier = Modifier,
    onClickedLogin: (String, String) -> Unit,
    onGoToForgotPassword: () -> Unit
) {
    var visible by rememberSaveable {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = visible) {
        visible = true
    }

    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("luongxuantrung@gmail.com") }
    var password by rememberSaveable { mutableStateOf("12345678") }

    AnimatedVisibility(
        visible,
        modifier = modifier,
        enter = fadeIn(
            animationSpec = tween(
                ANIMATION_TIME_DURATION,
                easing = LinearEasing,
                delayMillis = TIME_DELAY_DISPLAY_LOGO
            )
        )
    ) {
        Column {
            InputEmailComponent(email, onTextChanged = { email = it })
            Spacer(modifier = Modifier.height(20.dp))
            InputPasswordComponent(
                password, onTextChanged = { password = it },
                onGoToForgotPassword = onGoToForgotPassword
            )
            Spacer(modifier = Modifier.height(20.dp))
            FilledButton(
                onClick = {
                    focusManager.clearFocus()
//                    onGoToHome.invoke()
                    onClickedLogin.invoke(email, password)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .testTag("btnLogin")
            ) {
                Text(text = stringResource(R.string.login))
            }
        }
    }
}

@Composable
fun InputEmailComponent(
    email: String, onTextChanged: (String) -> Unit,
) {
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
            imeAction = ImeAction.Next, keyboardType = KeyboardType.Email, autoCorrect = false
        )
    )
}

@Composable
fun InputPasswordComponent(
    password: String, onTextChanged: (String) -> Unit,
    onGoToForgotPassword: () -> Unit,
) {

    val localFocusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxWidth()) {
        MyTextField(
            value = password,
            onValueChange = onTextChanged,
            placeholder = {
                Text(
                    text = stringResource(R.string.password),
                    style = MaterialTheme.typography.bodyMedium,
                    color = White30,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 48.dp)
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("passwordInput"),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done, keyboardType = KeyboardType.Text, autoCorrect = false
            ),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
        )
        ClickableText(
            text = AnnotatedString(stringResource(R.string.forgot)),
            style = MaterialTheme.typography.bodyMedium.copy(color = White50),
//            color = White50,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp),
            onClick = {
                onGoToForgotPassword.invoke()
            })
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NimbleChallengeTheme {
        LoginContent(onClickedLogin = { _, _ -> run {} }, onGoToForgotPassword = {})
    }
}

@Preview
@Composable
fun LoginInputContentPreview() {
    NimbleChallengeTheme {
        LoginInputContent(onClickedLogin = { _, _ -> run {} }, onGoToForgotPassword = {})
    }
}
