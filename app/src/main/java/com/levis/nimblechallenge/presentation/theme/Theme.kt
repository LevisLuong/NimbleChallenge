package com.levis.nimblechallenge.presentation.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.levis.nimblechallenge.R

private val themeColors = lightColorScheme(
    primary = Color.White
)

@Composable
fun NimbleChallengeTheme(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier.paint(
            painter = painterResource(id = R.drawable.bg_window),
            contentScale = ContentScale.Crop,
        )
    ) {
        MaterialTheme(
            colorScheme = themeColors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}