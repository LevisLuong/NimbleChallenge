package com.levis.nimblechallenge.presentation.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.levis.nimblechallenge.core.utils.shimmerAnimation
import com.levis.nimblechallenge.presentation.theme.Black10
import com.levis.nimblechallenge.presentation.theme.GrayBlack
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme

@Composable
fun SurveyListLoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(GrayBlack)
    ) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Black10,
                            Black
                        )
                    )
                )
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            LoadingTopBar()
            LoadingBottomBar()
        }
    }
}

@Composable
fun LoadingTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier
                .weight(1F)
        ) {
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .width(117.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .shimmerAnimation()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .width(97.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .shimmerAnimation()
            )
        }
        Spacer(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .shimmerAnimation()
        )
    }
}

@Composable
fun LoadingBottomBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(44.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .shimmerAnimation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(253.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .shimmerAnimation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(125.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .shimmerAnimation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(318.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .shimmerAnimation()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .width(186.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .shimmerAnimation()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SurveyListPreview() {
    NimbleChallengeTheme {
        SurveyListLoading()
    }
}
