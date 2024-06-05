package com.levis.nimblechallenge.domain.usecases

import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.LoginRequest
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

@ExperimentalCoroutinesApi
class LoginUseCaseTest {

    private val authRepository: AuthRepository = mockk()
    private val testDispatcher = UnconfinedTestDispatcher()

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        loginUseCase = LoginUseCase(authRepository, testDispatcher)
    }

    @Test
    fun `login with valid credentials returns success`() = runTest {
        val expectedTokenDto = LoginTokenDto(
            accessToken = "test_access_token",
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = "test_refresh_token",
            createdAt = 1678886400
        )

        coEvery {
            authRepository.login(
                LoginRequest(
                    "testuser", "password"
                )
            )
        } returns expectedTokenDto

        val result = loginUseCase(LoginRequest("testuser", "password")).first()

        assert(result is UseCaseResult.Success)
        val actualTokenDto = (result as UseCaseResult.Success).value
        assert(actualTokenDto == expectedTokenDto)
    }

    @Test
    fun `login with invalid credentials emits Error with exception`() = runTest {
        val errorJson = """
        {
          "errors": [
            {
              "detail": "Your email or password is incorrect. Please try again.",
              "code": "invalid_email_or_password"
            }
          ]
        }
    """.trimIndent()
        val responseBodyError = errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            authRepository.login(
                LoginRequest("testuser", "wrongpassword")
            )
        } throws HttpException(Response.error<Any>(400, responseBodyError))

        val result = loginUseCase(LoginRequest("testuser", "wrongpassword")).first()

        assert(result is UseCaseResult.Error)
        assert(((result as UseCaseResult.Error).exception as DataException.Api).error?.errors?.firstOrNull()?.detail == "Your email or password is incorrect. Please try again.")
    }
}