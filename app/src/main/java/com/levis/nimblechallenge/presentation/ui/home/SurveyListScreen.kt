package com.levis.nimblechallenge.presentation.ui.home

import android.widget.Toast
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.levis.nimblechallenge.R
import com.levis.nimblechallenge.core.common.dummySurvey
import com.levis.nimblechallenge.core.utils.format
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import com.levis.nimblechallenge.presentation.theme.White20
import com.levis.nimblechallenge.presentation.ui.components.DotsIndicator
import com.levis.nimblechallenge.presentation.ui.components.ProfileDrawer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun SurveyScreen(
    viewModel: SurveyListViewModel = hiltViewModel(),
    onGoToLogin: () -> Unit,
    onGoToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val surveyList = viewModel.surveyList.collectAsLazyPagingItems()

    LaunchedEffect(key1 = Unit) {
        viewModel.errorMutableSharedFlow.collectLatest {
            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(viewModel.navEvent) {
        viewModel.navEvent.collectLatest {
            when (it) {
                SurveyNavEvent.Login -> onGoToLogin.invoke()
            }
        }
    }

    when (val refreshState = surveyList.loadState.refresh) {
        is LoadState.Error -> {
            SurveyPageErrorMessage(
                message = refreshState.error.message ?: "An error occurred.",
                onRetryClick = {
                    surveyList.retry()
                }
            )
            Toast.makeText(
                context,
                refreshState.error.message ?: "Error fetching data.",
                Toast.LENGTH_SHORT
            ).show()
        }

        LoadState.Loading -> {
            SurveyListLoading()
        }

        else -> SurveyListContent(
            surveyList = surveyList,
            onLogOut = { viewModel.logout() },
            onGoToDetail = onGoToDetail
        )
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun SurveyListContent(
    surveyList: LazyPagingItems<SurveyModel>,
    onLogOut: () -> Unit,
    onGoToDetail: (String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val pageCount = surveyList.itemCount
    val pagerState = rememberPagerState(
        pageCount = { pageCount }
    )

    val isRefreshing = surveyList.loadState.refresh is LoadState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            scope.launch {
                pagerState.scrollToPage(0)
                surveyList.refresh()
            }
        },
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
            .pullRefresh(pullRefreshState)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            SurveyImage(surveyList.peek(page)?.largeCoverImageUrl ?: "")
        }

        if (surveyList.itemSnapshotList.isNotEmpty() &&
            surveyList[pagerState.currentPage] != null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                TopView(
                    survey = surveyList[pagerState.currentPage]!!,
                    onProfileClicked = { scope.launch { isDrawerOpen = true } }
                )
                BottomView(
                    pageCount = pageCount,
                    pagerState = pagerState,
                    survey = surveyList[pagerState.currentPage]!!,
                    navigateToDetails = onGoToDetail
                )
            }
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
                onLogOut = onLogOut,
                modifier = Modifier
                    .width(240.dp)
                    .align(Alignment.TopEnd)
            )
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = Color.DarkGray,
        )
    }
}

@Composable
fun TopView(
    survey: SurveyModel,
    onProfileClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = survey.createdAt?.format()?.uppercase() ?: "",
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
    survey: SurveyModel,
    navigateToDetails: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        DotsIndicator(
            modifier = Modifier.padding(bottom = 16.dp),
            totalDots = pageCount,
            selectedIndex = pagerState.currentPage,
            selectedColor = White,
            unSelectedColor = White20,
            indicatorSize = 8.dp,
            space = 5.dp
        )
        Text(
            text = survey.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                color = White,
                fontWeight = FontWeight.ExtraBold,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("surveyTitle"),
            textAlign = TextAlign.Left
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = survey.description,
                style = MaterialTheme.typography.bodyMedium.copy(color = White),
                textAlign = TextAlign.Left,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .weight(1F)
                    .padding(end = 16.dp)
                    .testTag("surveyDescription"),
            )
            IconButton(
                onClick = {
                    navigateToDetails.invoke(survey.id)
                },
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
                        Black,
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

@Preview
@Composable
fun TopViewPreview() {
    NimbleChallengeTheme {
        TopView(survey = dummySurvey, onProfileClicked = {})
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun BottomViewPreview() {
    NimbleChallengeTheme {
        BottomView(
            pageCount = 3,
            pagerState = rememberPagerState(
                initialPage = 0,
                pageCount = { 3 }
            ),
            survey = dummySurvey,
            navigateToDetails = {}
        )
    }
}

@Preview
@Composable
fun SurveyImagePreview() {
    NimbleChallengeTheme {
        SurveyImage("https://picsum.photos/200/300")
    }
}
