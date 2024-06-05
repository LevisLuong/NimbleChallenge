package com.levis.nimblechallenge.data.repository

import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.dtos.BaseDataResponse
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.ForgotPasswordRequest
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.response.ForgotPasswordResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class AuthRepositoryImplTest {

    private val api: Api = mockk()
    private val localDataSource: LocalDataSource = mockk(relaxed = true)
    private lateinit var authRepository: AuthRepositoryImpl

    @Before
    fun setup() {
        authRepository = AuthRepositoryImpl(api, localDataSource)
    }

    @Test
    fun `login with valid credentials returns LoginTokenDto and saves tokens`() = runTest {
        // Arrange
        val expectedTokenDto = LoginTokenDto(
            accessToken = "test_access_token",
            tokenType = "Bearer",
            expiresIn = 3600,
            refreshToken = "test_refresh_token",
            createdAt = 1678886400
        )
        val mockApiResponse = mockk<BaseDataResponse<LoginTokenDto>>()
        every { mockApiResponse.data } returns expectedTokenDto
        coEvery { api.login(any()) } returns mockApiResponse

        // Act
        val result = authRepository.login(LoginRequest("testuser", "password"))

        // Assert
        assertEquals(expectedTokenDto, result)

        // Verify side effects (saving tokens)
        coVerify {
            localDataSource.saveAccessToken(expectedTokenDto.accessToken.orEmpty())
            localDataSource.saveTokenType(expectedTokenDto.tokenType.orEmpty())
            localDataSource.saveRefreshToken(expectedTokenDto.refreshToken.orEmpty())
        }
    }

    @Test
    fun `login with invalid credentials throws HttpException`() = runTest {
        // Arrange
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
        val responseErrorException = Response.error<Any>(400, responseBodyError)
        coEvery { api.login(any()) } throws HttpException(responseErrorException)

        // Act & Assert (using try-catch)
        try {
            authRepository.login(LoginRequest("testuser", "wrongpassword"))
            error("HttpException should have been thrown") // Test should fail if no exception
        } catch (e: HttpException) {
            assertEquals(responseErrorException.code(), e.code())
            assertEquals(responseErrorException.errorBody(), e.response()?.errorBody())
        }
    }

    @Test
    fun `logout clears tokens from local storage`() = runTest {
        coEvery { api.logout(any()) } returns BaseDataResponse("")
        // Act
        authRepository.logout()

        // Assert
        coVerify {
            localDataSource.clearAll()
        }
    }

    @Test
    fun `forgotPassword calls api forgotPassword with correct email`() = runTest {
        // Arrange
        val email = "test@example.com"
        val response = ForgotPasswordResponse(ForgotPasswordResponse.MetaForgotPassword("message"))
        coEvery { api.forgotPassword(any()) } returns Response.success(response)

        // Act
        val result = authRepository.forgotPassword(
            ForgotPasswordRequest(
                ForgotPasswordRequest.UserForgotPassword(
                    email
                )
            )
        )
        assertEquals(result, response)
    }
}