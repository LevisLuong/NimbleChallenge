package com.levis.nimblechallenge.domain.usecases

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import com.levis.nimblechallenge.domain.repository.SurveyRepository
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.Date

class SurveyListUseCaseTest {

    private var surveyRepository: SurveyRepository = mockk()
    private lateinit var surveyListUseCase: SurveyListUseCase

    @Before
    fun setUp() {
        surveyListUseCase = SurveyListUseCase(surveyRepository)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
    }

    @Test
    fun `return survey paging data when request success`() = runTest {
        val surveyList = listOf(
            SurveyModel(
                id = "1",
                title = "Title",
                description = "This is description.",
                coverImageUrl = "url.image",
                createdAt = Date()
            )
        )

        coEvery { surveyRepository.getSurveyList() } returns flowOf(PagingData.from(surveyList))

        val result = surveyListUseCase()

        val items = result.asSnapshot()

        Assert.assertEquals(
            surveyList,
            items
        )
    }

    @Test
    fun `return exception when request fails`() = runTest {
        val exception = Exception()

        coEvery { surveyRepository.getSurveyList() } returns flow { throw exception }

        surveyListUseCase().catch {
            it shouldBe exception
        }.collect {}
    }
}