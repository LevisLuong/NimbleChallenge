package com.levis.nimblechallenge.presentation.ui.login

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
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.domain.mappers.mapError
import com.levis.nimblechallenge.domain.usecases.LoginUseCase
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
class LoginScreenTest {
    @MockK
    private lateinit var loginUseCase: LoginUseCase
    private val navController = mockk<NavHostController>()
    private lateinit var loginViewModel: LoginViewModel

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        loginViewModel = LoginViewModel(loginUseCase)

        composeTestRule.setContent {
            NimbleChallengeTheme {
                LoginScreen(
                    onGoToHome = {
                        navController.navigate(ScreenNavigation.Home.route)
                    },
                    onGoToForgotPassword = { },
                    viewModel = loginViewModel
                )
            }
        }
    }

    @Test
    fun loginScreen_displaysUiElements() {
        composeTestRule.onNodeWithTag("emailInput").assertIsDisplayed()
        composeTestRule.onNodeWithTag("passwordInput").assertIsDisplayed()
    }

    @Test
    fun test_LoginSuccess_Then_NavigateToHome() {

        coEvery {
            loginUseCase(any())
        } returns flowOf(UseCaseResult.Success(LoginTokenDto()))

        coEvery { navController.navigate(ScreenNavigation.Home.route) } returns Unit

        composeTestRule.onNodeWithTag("emailInput")
            .performTextInput("luongxuantrung@mail.com")

        composeTestRule.onNodeWithTag("passwordInput")
            .performTextInput("12345678")

        composeTestRule.onNodeWithTag("btnLogin")
            .performClick()

        verify { navController.navigate(ScreenNavigation.Home.route) }
    }

    @Test
    fun test_LoginFail_Then_ShowToastError() {
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
            loginUseCase(any())
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

        composeTestRule.onNodeWithTag("passwordInput")
            .performTextInput("12345678")

        composeTestRule.onNodeWithTag("btnLogin")
            .performClick()

        composeTestRule.onNodeWithText("Your email or password is incorrect. Please try again.")
    }

}