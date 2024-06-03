package com.levis.nimblechallenge.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.presentation.theme.GrayBlack
import com.radusalagean.infobarcompose.BaseInfoBarMessage

class CustomSnackBarMessage(
    val title: String,
    val textString: String,
    val icon: ImageVector = Icons.Default.Notifications,
    val iconColor: Color = Color.White,
    val textColor: Color = Color.White,
    override val backgroundColor: Color? = GrayBlack,
    override val displayTimeSeconds: Int? = 4,
) : BaseInfoBarMessage() {
    override val containsControls: Boolean = false
}

val contentCustomSnackBar: @Composable (CustomSnackBarMessage) -> Unit = { message ->
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterVertically),
            imageVector = message.icon,
            contentDescription = null,
            tint = message.iconColor
        )
        Column {
            Text(
                text = message.title,
                color = message.textColor,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = message.textString,
                color = message.textColor,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }
}