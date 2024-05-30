package com.levis.nimblechallenge.presentation.ui.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.shapes

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize()
            .background(color = Color(0xFF000000))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_blur_overlay),
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0x00000000),
                            Color(0xFF000000)
                        )
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    painter = painterResource(id = R.drawable.ic_nimble_logo),
                    modifier = Modifier
                        .width(168.dp),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )

                Spacer(modifier = Modifier.height(109.dp))

                InputEmailComponent(
                    labelValue = stringResource(id = R.string.login),
                    onTextChanged = {
//                        loginViewModel.onEvent(LoginUIEvent.EmailChanged(it))
                    },
                    errorStatus = false
                )
                Spacer(modifier = Modifier.height(20.dp))

                InputPasswordComponent(
                    labelValue = stringResource(id = R.string.password),
                    onTextSelected = {
//                        loginViewModel.onEvent(LoginUIEvent.PasswordChanged(it))
                    },
                    errorStatus = false
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(56.dp),
                    onClick = {
//                        onButtonClicked.invoke()
                    },
                    contentPadding = PaddingValues(),
                    colors = ButtonDefaults.buttonColors(Color.Transparent),
                    shape = RoundedCornerShape(10.dp),
                    enabled = true
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(56.dp)
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(10.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(id = R.string.login),
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputEmailComponent(
    labelValue: String,
    onTextChanged: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val textValue = remember {
        mutableStateOf("")
    }
    val localFocusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clip(shapes.small),
        label = { Text(text = labelValue) },
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            textColor = BgColor,
//            focusedBorderColor = ColorHolder,
//            focusedLabelColor = ColorHolder,
//            cursorColor = ColorHolder,
//            placeholderColor = ColorHolder,
//            backgroundColor = ColorBackgroundField
//        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        isError = !errorStatus
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputPasswordComponent(
    labelValue: String,
    onTextSelected: (String) -> Unit,
    errorStatus: Boolean = false
) {

    val localFocusManager = LocalFocusManager.current
    val password = remember {
        mutableStateOf("")
    }

    val passwordVisible = remember {
        mutableStateOf(false)
    }

    TextField(
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .clip(shapes.small),
        label = { Text(text = labelValue) },
//        colors = TextFieldDefaults.outlinedTextFieldColors(
//            textColor = BgColor,
//            focusedBorderColor = BgColor,
//            focusedLabelColor = BgColor,
//            cursorColor = BgColor,
//            backgroundColor = ColorBackgroundField
//        ),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        singleLine = true,
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        trailingIcon = {

            val iconImage = if (passwordVisible.value) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = !errorStatus
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    NimbleChallengeTheme {
        LoginScreen()
    }
}