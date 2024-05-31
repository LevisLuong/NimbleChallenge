package com.levis.nimblechallenge.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val themeColors = lightColorScheme(
    primary = Color.White
)

@Composable
fun NimbleChallengeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = themeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}