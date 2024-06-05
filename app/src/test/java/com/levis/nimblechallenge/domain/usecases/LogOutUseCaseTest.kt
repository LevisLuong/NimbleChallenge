package com.levis.nimblechallenge.domain.usecases

import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.domain.model.error.DataException
import com.levis.nimblechallenge.domain.repository.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class LogOutUseCaseTest {

    private var authRepository: AuthRepository = mockk()
    private lateinit var logoutUseCase: LogoutUseCase
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        logoutUseCase = LogoutUseCase(authRepository, testDispatcher)
    }

    @Test
    fun `return Unit when logout is successful`() = runTest {

        coEvery {
            authRepository.logout()
        } returns Unit

        val result = logoutUseCase().first()
        // Assert
        assert(result is UseCaseResult.Success)
    }

    @Test
    fun `return exception when logout fails`() = runTest {
        // Arrange
        val errorJson = """
        {
          "errors": [
            {
              "detail": "You are not authorized to revoke this token",
              "code": "unauthorized_client"
            }
          ]
        }
    """.trimIndent()
        val responseBodyError = errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            authRepository.logout()
        } throws HttpException(Response.error<Any>(403, responseBodyError))

        // Act
        val result = logoutUseCase().first()

        // Assert
        assert(result is UseCaseResult.Error)
        assert(((result as UseCaseResult.Error).exception as DataException.Api).error?.errors?.firstOrNull()?.detail == "You are not authorized to revoke this token")
    }
}