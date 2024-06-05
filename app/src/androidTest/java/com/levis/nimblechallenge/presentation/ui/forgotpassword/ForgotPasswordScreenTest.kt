package com.levis.nimblechallenge.presentation.ui.forgotpassword

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavHostController
import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.data.network.response.ForgotPasswordResponse
import com.levis.nimblechallenge.domain.mappers.mapError
import com.levis.nimblechallenge.domain.usecases.ForgotPasswordUseCase
import com.levis.nimblechallenge.presentation.navigation.ScreenNavigation
import com.levis.nimblechallenge.presentation.theme.NimbleChallengeTheme
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalTestApi
@ExperimentalFoundationApi
@ExperimentalComposeUiApi
class ForgotPasswordScreenTest {
    @MockK
    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase
    private val navController = mockk<NavHostController>()
    private lateinit var viewModel: ForgotPasswordViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = ForgotPasswordViewModel(forgotPasswordUseCase)

        composeTestRule.setContent {
            NimbleChallengeTheme {
                ForgotPasswordScreen(
                    viewModel = viewModel,
                    onGoToLogin = {
                        navController.navigate(ScreenNavigation.Login.route)
                    }
                )
            }
        }
    }

    @Test
    fun forgotPasswordScreen_displaysUiElements() {
        composeTestRule.onNodeWithTag("emailInput").assertIsDisplayed()
    }

    @Test
    fun test_forgotPassword_success_sent_request() {

        coEvery {
            forgotPasswordUseCase(any())
        } returns flowOf(
            UseCaseResult.Success(
                ForgotPasswordResponse(
                    ForgotPasswordResponse.MetaForgotPassword(
                        "success"
                    )
                )
            )
        )

        composeTestRule.onNodeWithTag("emailInput")
            .performTextInput("luongxuantrung@mail.com")

        composeTestRule.onNodeWithTag("btnReset")
            .performClick()

        composeTestRule.onNodeWithText("success")
    }

    @Test
    fun test_forgotPassword_Fail_Then_ShowToastError() {
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
        val responseBodyError = errorJson.toResponseBody("application/json".toMediaTypeOrNull())
        coEvery {
            forgotPasswordUseCase(any())
        } returns flowOf(
            UseCaseResult.Error(
                HttpException(
                    Response.error<Any>(
                        400,
                        responseBodyError
                    )
                ).mapError()
            )
        )

        composeTestRule.onNodeWithTag("emailInput")
            .performTextInput("luongxuantrung@mail.com")

        composeTestRule.onNodeWithTag("btnReset")
            .performClick()

        composeTestRule.onNodeWithText("Client authentication failed due to unknown client, no client authentication included, or unsupported authentication method.")
    }

    @Test
    fun test_forgotPassword_back_to_login() {

        coEvery { navController.navigate(ScreenNavigation.Login.route) } returns Unit

        composeTestRule.onNodeWithTag("btnBack")
            .performClick()

        verify {
            navController.navigate(ScreenNavigation.Login.route)
        }
    }
}