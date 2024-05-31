package com.levis.nimblechallenge.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.Grey90
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme

@Composable
fun ProfileDrawer(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit
) {
    ProfileDrawerContent(modifier = modifier, onLogOut = onLogOut)
}

@Composable
fun ProfileDrawerContent(
    modifier: Modifier = Modifier,
    onLogOut: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Grey90)
            .padding(
                vertical = 32.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mai",
                style = MaterialTheme.typography.titleLarge,
                color = White,
                modifier = Modifier
                    .weight(1f)
            )
            Image(
                painter = painterResource(id = R.drawable.img_avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(
            Modifier
                .height(0.3.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Color.White.copy(alpha = 0.5f))
        )
        Spacer(modifier = Modifier.height(35.dp))
        Text(
            text = stringResource(R.string.logout),
            style = MaterialTheme.typography.bodyLarge,
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp)
                .clickable { onLogOut() }
        )
        Spacer(modifier = Modifier.weight(1F))
        Text(
            text = "v0.1.0 (1562903885)",
            style = MaterialTheme.typography.labelSmall,
            color = White,
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(start = 20.dp)
                .clickable { onLogOut() }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileDrawerContentPreview() {
    NimbleChallengeTheme {
        ProfileDrawerContent(onLogOut = {})
    }
}
