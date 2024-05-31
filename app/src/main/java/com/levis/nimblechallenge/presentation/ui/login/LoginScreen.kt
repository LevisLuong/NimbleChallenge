package com.levis.nimblechallenge.presentation.ui.login

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.White30
import com.levis.nimblechallenge.presentation.theme.White50
import com.levis.nimblechallenge.presentation.ui.components.FilledButton
import com.levis.nimblechallenge.presentation.ui.components.MyTextField

@Composable
fun LoginScreen(
    onGoToHome: () -> Unit
) {
    var bgWindow by remember { mutableIntStateOf(R.drawable.bg_window) }
    LaunchedEffect(key1 = Unit) {
        bgWindow = R.drawable.bg_blur_overlay
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            bgWindow,
            animationSpec = tween(500),
            label = "Ani change background image"
        ) { targetState ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .paint(
                        painterResource(targetState),
                        contentScale = ContentScale.FillBounds
                    ),
            )
        }
        ImageLogoComponent()
        LoginContent(
            Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(24.dp),
            onGoToHome
        )
    }
}

@Composable
fun ImageLogoComponent(modifier: Modifier = Modifier) {
    // Make animation of this image from center height to top
    val startPosition = Offset(0f, 0f)
    val endPosition = Offset(0f, -200f)
    val position = remember { Animatable(startPosition, Offset.VectorConverter) }
    val animatedSizeOffset by remember { mutableStateOf(Animatable(0f)) }

    LaunchedEffect(key1 = Unit) {
        Log.d("trunglx", "start animation position")
        position.animateTo(
            targetValue = endPosition,
            animationSpec = keyframes {
                durationMillis = 500
            }
        )
    }
    LaunchedEffect(key1 = Unit) {
        Log.d("trunglx", "start animation position")
        animatedSizeOffset.animateTo(
            targetValue = -35f,
            animationSpec = keyframes {
                durationMillis = 500
            }
        )
    }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nimble_logo),
            modifier = Modifier
                .width(animatedSizeOffset.value.dp + 200.dp)
                .offset(y = position.value.y.dp),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    onGoToHome: () -> Unit
) {
    var visible by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = Unit) {
        visible = true
    }

    val focusManager = LocalFocusManager.current

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    AnimatedVisibility(
        visible,
        modifier = modifier,
        enter = fadeIn(animationSpec = tween(500))
    ) {
        Column() {
            InputEmailComponent(email, onTextChanged = { email = it })
            Spacer(modifier = Modifier.height(20.dp))
            InputPasswordComponent(password, onTextChanged = { password = it })
            Spacer(modifier = Modifier.height(20.dp))
            FilledButton(
                onClick = {
                    focusManager.clearFocus()
                    onGoToHome.invoke()
//                onClickedLogIn.invoke(email, password)
                },
                modifier = Modifier
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
    email: String,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
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
            imeAction = ImeAction.Next,
            keyboardType = KeyboardType.Email,
            autoCorrect = false
        )
    )
}

@Composable
fun InputPasswordComponent(
    password: String,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current

    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.forgot),
            style = MaterialTheme.typography.bodyMedium,
            color = White50,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp)
                .clickable {}
        )
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
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                autoCorrect = false
            ),
            keyboardActions = KeyboardActions(onDone = { localFocusManager.clearFocus() })
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NimbleChallengeTheme {
        LoginScreen(onGoToHome = {})
    }
}