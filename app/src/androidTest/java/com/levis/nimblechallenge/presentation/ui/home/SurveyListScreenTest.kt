package com.levis.nimblechallenge.presentation.ui.home

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.NavHostController
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.levis.nimblechallenge.core.common.dummySurvey
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import com.levis.nimblechallenge.presentation.navigation.ScreenNavigation
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SurveyListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val navController = mockk<NavHostController>()
    @MockK
    private lateinit var context: Context
    private lateinit var viewModel: SurveyListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        every { context.getString(any()) } returns "" // Mocking context strings
        viewModel = mockk<SurveyListViewModel>(relaxed = true).apply {
            every { surveyList } returns Pager(PagingConfig(pageSize = 20)) {
                FakePagingSource()
            }.flow
            every { navEvent } returns MutableSharedFlow()
            every { errorMutableSharedFlow } returns MutableSharedFlow()
        }
    }

    @Test
    fun testSurveyListErrorState() {
        val errorMessage = "An error occurred."
        val errorPagingSource = object : PagingSource<Int, SurveyModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyModel> {
                return LoadResult.Error(Exception(errorMessage))
            }

            override fun getRefreshKey(state: PagingState<Int, SurveyModel>): Int? = null
        }

        every { viewModel.surveyList } returns Pager(PagingConfig(pageSize = 20)) {
            errorPagingSource
        }.flow

        composeTestRule.setContent {
            SurveyScreen(viewModel = viewModel, onGoToLogin = {}, onGoToDetail = {})
        }

        composeTestRule.onNodeWithTag("surveyErrorScreen").assertIsDisplayed()
    }

    @Test
    fun testSurveyListContentDisplayed() {
        val fakeSurveyList = listOf(
            dummySurvey,
            dummySurvey
        )

        val fakePagingSource = object : PagingSource<Int, SurveyModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyModel> {
                return LoadResult.Page(
                    data = fakeSurveyList,
                    prevKey = null,
                    nextKey = null
                )
            }

            override fun getRefreshKey(state: PagingState<Int, SurveyModel>): Int? = null
        }

        every { viewModel.surveyList } returns Pager(PagingConfig(pageSize = 20)) {
            fakePagingSource
        }.flow

        composeTestRule.setContent {
            SurveyScreen(viewModel = viewModel, onGoToLogin = {}, onGoToDetail = {})
        }

        composeTestRule.onNodeWithText(dummySurvey.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(dummySurvey.description).assertIsDisplayed()
    }
    @Test
    fun testLogoutEvent(): Unit = runBlocking {
        val navEvent = MutableSharedFlow<SurveyNavEvent>()
        every { viewModel.navEvent } returns navEvent
        coEvery { navController.navigate(ScreenNavigation.Login.route) } returns Unit
        composeTestRule.setContent {
            SurveyScreen(viewModel = viewModel, onGoToLogin = {
                navController.navigate(ScreenNavigation.Login.route)
            }, onGoToDetail = {})
        }

        viewModel.logout()

        navEvent.emit(SurveyNavEvent.Login)

        verify { navController.navigate(ScreenNavigation.Login.route) }
    }
}

class FakePagingSource : PagingSource<Int, SurveyModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SurveyModel> {
        return LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SurveyModel>): Int? = null
}