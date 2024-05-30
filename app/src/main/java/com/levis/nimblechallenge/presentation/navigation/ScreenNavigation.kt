package com.levis.nimblechallenge.presentation.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class ScreenNavigation(
    val route: String,
    val navArguments: List<NamedNavArgument> = emptyList()
) {
    data object Login : ScreenNavigation("login")

    data object Home : ScreenNavigation("home")

    data object SurveyDetail : ScreenNavigation(
        route = "surveyDetail/{surveyId}",
        navArguments = listOf(navArgument("surveyId") {
            type = NavType.StringType
        })
    ) {
        fun createRoute(surveyId: String) = "surveyDetail/${surveyId}"
    }
}