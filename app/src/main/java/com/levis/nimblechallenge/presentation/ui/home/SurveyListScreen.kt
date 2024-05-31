package com.levis.nimblechallenge.presentation.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.presentation.theme.Black10
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.White20
import com.levis.nimblechallenge.presentation.ui.components.DotsIndicator
import com.levis.nimblechallenge.presentation.ui.components.ProfileDrawer
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SurveyListContent() {
    val scope = rememberCoroutineScope()
    val pageCount = 3
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )
    var isDrawerOpen by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) { scope.launch { isDrawerOpen = false } }
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            SurveyImage(imageUrl = "https://picsum.photos/200/300")
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            TopView(
                survey = "",
                onProfileClicked = { scope.launch { isDrawerOpen = true } }
            )
            BottomView(
                pageCount = pageCount,
                pagerState = pagerState,
                survey = "",
                navigateToDetails = {}
            )
        }
        AnimatedVisibility(
            visible = isDrawerOpen,
            enter = slideInHorizontally(initialOffsetX = { w -> w }) + expandHorizontally(
                expandFrom = Alignment.End
            ),
            exit = slideOutHorizontally(targetOffsetX = { w -> w }) + shrinkHorizontally(
                shrinkTowards = Alignment.End
            ),
            modifier = Modifier
                .width(240.dp)
                .align(Alignment.TopEnd)
        ) {
            ProfileDrawer(
                onLogOut = {},
                modifier = Modifier
                    .width(240.dp)
                    .align(Alignment.TopEnd)
            )
        }
    }
}

@Composable
fun TopView(
    survey: Any,
    onProfileClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Text vurvey".uppercase(),
            style = MaterialTheme.typography.bodySmall.copy(color = White),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            textAlign = TextAlign.Left
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.titleLarge.copy(color = White),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 16.dp)
            )
            Image(
                painter = painterResource(id = R.drawable.img_avatar),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(36.dp)
                    .clickable { onProfileClicked.invoke() }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomView(
    pageCount: Int,
    pagerState: PagerState,
    survey: Any,
    navigateToDetails: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
//        PagerIndicator(pageCount = pageCount, pagerState = pagerState)
        DotsIndicator(
            totalDots = pageCount,
            selectedIndex = pagerState.currentPage,
            selectedColor = White,
            unSelectedColor = White20,
            indicatorSize = 8.dp,
            space = 5.dp
        )
        Text(
            text = "Career training and development",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = White,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Building a workplace culture that prioritizes belonging and inclusion asdas das",
                style = MaterialTheme.typography.bodyMedium.copy(color = White),
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 16.dp)
            )
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(56.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(color = White, shape = CircleShape)
                        .size(56.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .size(36.dp)
                    )
                }
            }
        }
    }
}

//@OptIn(ExperimentalFoundationApi::class)
//@Composable
//fun PagerIndicator(
//    pageCount: Int,
//    pagerState: PagerState
//) {
//    Row(
//        Modifier
//            .height(50.dp),
//        horizontalArrangement = Arrangement.Center,
//        verticalAlignment = Alignment.CenterVertically,
//    ) {
//        repeat(pageCount) { iteration ->
//            val color = if (pagerState.currentPage == iteration) White else Color.Gray
//            Box(
//                modifier = Modifier
//                    .padding(vertical = 8.dp, horizontal = 4.dp)
//                    .background(color, CircleShape)
//                    .size(8.dp)
//            )
//        }
//    }
//}

@Composable
fun SurveyImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Black10,
                        Black
                    )
                )
            )
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TopViewPreview() {
    NimbleChallengeTheme {
        TopView(survey = "", onProfileClicked = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
fun BottomViewPreview() {
    NimbleChallengeTheme {
        BottomView(
            pageCount = 3,
            pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { 3 }
            ),
            survey = "",
            navigateToDetails = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SurveyImagePreview() {
    NimbleChallengeTheme {
        SurveyImage("https://picsum.photos/200/300")
    }
}
