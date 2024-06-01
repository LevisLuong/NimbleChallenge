package com.levis.nimblechallenge.presentation.ui.login

sealed interface LoginNavEvent {
    data object Home : LoginNavEvent
    data object ForgotPassword : LoginNavEvent
}