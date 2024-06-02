package com.levis.nimblechallenge.presentation.ui.forgotpassword

import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
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
import com.levis.nimblechallenge.presentation.ui.components.FilledButton
import com.levis.nimblechallenge.presentation.ui.components.MyTextField
import kotlinx.coroutines.flow.collectLatest


@Composable
fun ForgotPasswordScreen(
    onGoToLogin: () -> Unit,
    viewModel: ForgotPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val isLoadingState = viewModel.loadingMutableStateFlow.collectAsState().value

    var email by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(viewModel.responseForgotPassword) {
        viewModel.responseForgotPassword.collectLatest {
            if (it) {
                // TODO show snack bar success
            }
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.errorMutableSharedFlow.collectLatest {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
            // TODO show snack bar error
        }
    }

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
                    InputEmailComponent(email, onTextChanged = { email = it })
                    Spacer(modifier = Modifier.height(24.dp))
                    FilledButton(
                        onClick = {
                            focusManager.clearFocus()
                            viewModel.forgotPassword(email)
                        }, modifier = Modifier
                            .fillMaxWidth()
                            .testTag("btnReset")
                    ) {
                        Text(text = stringResource(R.string.reset))
                    }
                }
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
fun ImageLogoComponent(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_nimble_logo),
            modifier = Modifier
                .width(165.dp),
            contentDescription = null,
            contentScale = ContentScale.FillWidth
        )
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
        ForgotPasswordScreen(onGoToLogin = {})
    }
}