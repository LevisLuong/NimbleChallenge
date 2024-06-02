package com.levis.nimblechallenge.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levis.nimblechallenge.presentation.ui.home.SurveyScreen
import com.levis.nimblechallenge.presentation.ui.login.LoginScreen
import com.levis.nimblechallenge.presentation.ui.splash.SplashScreen

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
    NavHost(navController = navController, startDestination = ScreenNavigation.Splash.route) {
        composable(route = ScreenNavigation.Splash.route) {
            SplashScreen(
                onGoToHome = {
                    navController.navigate(ScreenNavigation.Home.route) {
                        popUpTo(ScreenNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToLogin = {
                    navController.navigate(ScreenNavigation.Login.route) {
                        popUpTo(ScreenNavigation.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(route = ScreenNavigation.Login.route) {
            LoginScreen(
                onGoToHome = {
                    navController.navigate(ScreenNavigation.Home.route) {
                        popUpTo(ScreenNavigation.Login.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToForgotPassword = {

                }
            )
        }
        composable(route = ScreenNavigation.Home.route) {
            SurveyScreen(
                onGoToLogin = {
                    navController.navigate(ScreenNavigation.Login.route) {
                        popUpTo(ScreenNavigation.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onGoToDetail = {

                }
            )
        }
    }
}