package com.levis.nimblechallenge.presentation.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.presentation.theme.TextBlack
import com.levis.nimblechallenge.presentation.theme.shapes

val ButtonHeight = 56.dp

@Composable
fun FilledButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
        .copy(fontWeight = FontWeight.ExtraBold, color = TextBlack),
    enabled: Boolean = true,
    height: Dp = ButtonHeight,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.White,
        contentColor = Color.White
    ),
    content: @Composable RowScope.() -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .heightIn(height),
        enabled = enabled,
        shape = shapes.small,
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 0.dp),
        colors = colors,
        content = {
            ProvideTextStyle(value = textStyle) {
                content()
            }
        }
    )
}

@Composable
fun filledButtonPreview(
    color: Color = Color.White
) = ButtonDefaults.buttonColors(
    containerColor = color,
    contentColor = Color.White
)
