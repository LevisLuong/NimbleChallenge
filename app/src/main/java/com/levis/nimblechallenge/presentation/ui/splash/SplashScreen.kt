package com.levis.nimblechallenge.presentation.ui.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.navigation.ScreenNavigation
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navController: NavHostController = rememberNavController(),
) {
    val rememberCoroutineScope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        rememberCoroutineScope.launch {
            delay(2000)
            navController.navigate(ScreenNavigation.Login.route) {
                popUpTo(ScreenNavigation.Splash.route) {
                    inclusive = true
                }
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