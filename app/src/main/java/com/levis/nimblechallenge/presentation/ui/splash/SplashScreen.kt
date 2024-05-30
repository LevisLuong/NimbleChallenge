package com.levis.nimblechallenge.presentation.ui.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
    LaunchedEffect(key1 = Unit) {
        rememberCoroutineScope.launch {
            delay(2000)
            navController.navigate(ScreenNavigation.Login.route)
        }
    }
    SplashContent()
}

@Composable
private fun SplashContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(202.dp, 48.dp),
            painter = painterResource(id = R.drawable.ic_nimble_logo),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun SplashContentPreview() {
    NimbleChallengeTheme {
        SplashContent()
    }
}