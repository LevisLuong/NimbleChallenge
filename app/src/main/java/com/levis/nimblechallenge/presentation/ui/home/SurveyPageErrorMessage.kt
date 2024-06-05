package com.levis.nimblechallenge.presentation.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme

@Composable
fun SurveyPageErrorMessage(
    modifier: Modifier = Modifier,
    message: String,
    onRetryClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().testTag("surveyErrorScreen"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(message, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.size(20.dp))
        Button(
            onClick = onRetryClick,
            shape = CircleShape,
        ) {
            Text(text = stringResource(R.string.retry))
        }
    }
}

@Preview
@Composable
fun SurveyPageErrorMessagePreview() {
    NimbleChallengeTheme {
        SurveyPageErrorMessage(
            modifier = Modifier,
            message = "Error",
            onRetryClick = {},
        )
    }
}