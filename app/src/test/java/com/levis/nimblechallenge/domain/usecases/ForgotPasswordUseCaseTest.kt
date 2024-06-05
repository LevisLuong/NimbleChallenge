package com.levis.nimblechallenge.domain.usecases

import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.data.network.request.ForgotPasswordRequest
import com.levis.nimblechallenge.data.network.response.ForgotPasswordResponse
import com.levis.nimblechallenge.domain.model.error.DataException
import com.levis.nimblechallenge.domain.repository.AuthRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response


@ExperimentalCoroutinesApi
class ForgotPasswordUseCaseTest {

    @MockK
    private lateinit var authRepository: AuthRepository

    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        forgotPasswordUseCase = ForgotPasswordUseCase(authRepository, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `invoke calls forgotPassword on repository and returns success`() = runTest {

        val expectedData =
            ForgotPasswordResponse(ForgotPasswordResponse.MetaForgotPassword("success"))

        val request =
            ForgotPasswordRequest(ForgotPasswordRequest.UserForgotPassword("test@email.com"))
        coEvery { authRepository.forgotPassword(request) } returns expectedData
        val result =
            forgotPasswordUseCase(ForgotPasswordRequest(ForgotPasswordRequest.UserForgotPassword("test@email.com"))).first()

        assert(result is UseCaseResult.Success)
        val actualData = (result as UseCaseResult.Success).value
        assert(actualData == expectedData)
    }

    @Test
    fun `invoke calls forgotPassword on repository and returns error`() = runTest {
        val errorJson = """
        {
          "errors": [
            {
              "detail": "Client authentication failed due to unknown client, no client authentication included, or unsupported authentication method.",
              "code": "invalid_client"
            }
          ]
        }
    """.trimIndent()

        val request = ForgotPasswordRequest(ForgotPasswordRequest.UserForgotPassword("email"))
        val responseBodyError = errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            authRepository.forgotPassword(
                request
            )
        } throws HttpException(Response.error<Any>(403, responseBodyError))

        val result = forgotPasswordUseCase(request).first()

        assert(result is UseCaseResult.Error)
        assert(((result as UseCaseResult.Error).exception as DataException.Api).error?.errors?.firstOrNull()?.detail == "Client authentication failed due to unknown client, no client authentication included, or unsupported authentication method.")
    }
}