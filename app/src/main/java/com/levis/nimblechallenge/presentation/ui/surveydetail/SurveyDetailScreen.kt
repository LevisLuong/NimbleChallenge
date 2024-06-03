package com.levis.nimblechallenge.presentation.ui.surveydetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.ui.components.FilledButton

@Composable
fun SurveyDetailScreen(
    surveyId: String?,
    onBackRoute: () -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            Text("Open ID ${surveyId}")
            FilledButton(onClick = { onBackRoute() }) {
                Text("Back to home")
            }
        }
    }
}

@Preview
@Composable
fun SurveyDetailPreview() {
    NimbleChallengeTheme {
        SurveyDetailScreen(surveyId = null, onBackRoute = {})
    }
}