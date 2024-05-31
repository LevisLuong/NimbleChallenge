package com.levis.nimblechallenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.levis.nimblechallenge.presentation.navigation.NimbleChallengeApp
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.ui.splash.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NimbleChallengeActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition {
            splashViewModel.isSplashShow.value
        }
        enableEdgeToEdge()
        setContent {
            NimbleChallengeTheme {
                NimbleChallengeApp()
            }
        }
    }
}