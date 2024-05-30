package com.levis.nimblechallenge.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levis.nimblechallenge.presentation.ui.login.LoginScreen

@Composable
fun NimbleChallengeApp() {
    val navController = rememberNavController()
    NimbleChallengeNavHost(
        navController = navController
    )
}

@Composable
fun NimbleChallengeNavHost(
    navController: NavHostController
) {
    val activity = (LocalContext.current as Activity)
    NavHost(navController = navController, startDestination = ScreenNavigation.Login.route) {
        composable(route = ScreenNavigation.Login.route) {
            LoginScreen()
        }
    }
}