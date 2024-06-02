package com.levis.nimblechallenge.presentation.ui.splash

sealed interface SplashNavEvent {
    data object Login : SplashNavEvent
    data object Home : SplashNavEvent
}