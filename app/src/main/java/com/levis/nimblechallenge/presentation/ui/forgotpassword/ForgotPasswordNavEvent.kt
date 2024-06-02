package com.levis.nimblechallenge.presentation.ui.forgotpassword

sealed interface ForgotPasswordNavEvent {
    data object Login : ForgotPasswordNavEvent
}