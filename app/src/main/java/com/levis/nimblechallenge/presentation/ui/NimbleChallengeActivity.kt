package com.levis.nimblechallenge.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.levis.nimblechallenge.presentation.navigation.NimbleChallengeApp
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NimbleChallengeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NimbleChallengeTheme {
                NimbleChallengeApp()
            }
        }
    }
}