package com.levis.nimblechallenge.presentation.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    onGoToHome: () -> Unit,
    onGoToLogin: () -> Unit
) {
    LaunchedEffect(viewModel.navEvent) {
        viewModel.navEvent.collectLatest {
            when (it) {
                SplashNavEvent.Home -> onGoToHome.invoke()
                SplashNavEvent.Login -> onGoToLogin.invoke()
            }
        }
    }

    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painter = painterResource(id = R.drawable.bg_window),
                contentScale = ContentScale.FillBounds
            ),
    )
}

@Preview
@Composable
fun SplashContentPreview() {
    NimbleChallengeTheme {
        SplashContent()
    }
}