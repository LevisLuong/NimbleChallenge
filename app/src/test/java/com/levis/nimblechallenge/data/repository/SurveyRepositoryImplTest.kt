package com.levis.nimblechallenge.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.domain.mappers.toModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
@OptIn(ExperimentalPagingApi::class)
class SurveyRepositoryImplTest {

    private val api: Api = mockk(relaxed = true)
    private val pager: Pager<Int, SurveyEntity> = mockk()
    private lateinit var surveyRepository: SurveyRepositoryImpl

    @Before
    fun setup() {
        surveyRepository = SurveyRepositoryImpl(api, pager)
        mockkStatic(Log::class)
        every { Log.isLoggable(any(), any()) } returns false
    }

    @Test
    fun `getSurveyList returns PagingData with mapped SurveyModels`() = runTest {
        // Arrange
//        val logger = TestLogger()
        val surveyEntities = listOf(
            SurveyEntity(
                "1",
                "Title 1",
                "Description 1",
                "https://example.com/image1.jpg",
                createdAt = ""
            ),
            SurveyEntity(
                "2",
                "Title 2",
                "Description 2",
                "https://example.com/image2.jpg",
                createdAt = ""
            )
        )
        val expectedSurveyModels = surveyEntities.map { it.toModel() }
        val pagingData = PagingData.from(surveyEntities)

        coEvery { pager.flow } returns flowOf(pagingData)

        // Act
        val resultFlow = surveyRepository.getSurveyList()

        val items = resultFlow.asSnapshot()

        print(items)
        // Assert
        assertEquals(
            expectedSurveyModels,
            items
        ) // Compare the contents of PagingData
    }
}