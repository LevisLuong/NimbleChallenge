package com.levis.nimblechallenge.presentation.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.levis.nimblechallenge.presentation.ui.forgotpassword.ForgotPasswordScreen
import com.levis.nimblechallenge.presentation.ui.home.SurveyScreen
import com.levis.nimblechallenge.presentation.ui.login.LoginScreen
import com.levis.nimblechallenge.presentation.ui.splash.SplashScreen
import com.levis.nimblechallenge.presentation.ui.surveydetail.SurveyDetailScreen

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
    NavHost(
        navController = navController,
        startDestination = ScreenNavigation.Splash.route,
    ) {
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
                    navController.navigate(ScreenNavigation.ForgotPassword.route)
                }
            )
        }
        composable(route = ScreenNavigation.ForgotPassword.route) {
            ForgotPasswordScreen(
                onGoToLogin = {
                    navController.popBackStack()
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
                onGoToDetail = { surveyId ->
                    navController.navigate(ScreenNavigation.SurveyDetail.createRoute(surveyId))
                }
            )
        }
        composable(
            route = ScreenNavigation.SurveyDetail.route,
            arguments = ScreenNavigation.SurveyDetail.navArguments
        ) {
            val surveyId = it.arguments?.getString("surveyId")
            SurveyDetailScreen(
                surveyId = surveyId,
                onBackRoute = {
                    navController.popBackStack()
                }
            )
        }
    }
}