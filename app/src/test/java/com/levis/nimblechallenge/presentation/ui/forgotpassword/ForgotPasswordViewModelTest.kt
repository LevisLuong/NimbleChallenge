package com.levis.nimblechallenge.presentation.ui.forgotpassword

import app.cash.turbine.test
import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.data.network.response.ForgotPasswordResponse
import com.levis.nimblechallenge.domain.usecases.ForgotPasswordUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class ForgotPasswordViewModelTest {

    @MockK
    private lateinit var forgotPasswordUseCase: ForgotPasswordUseCase

    private lateinit var forgotPasswordViewModel: ForgotPasswordViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        forgotPasswordViewModel = ForgotPasswordViewModel(forgotPasswordUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `success call forgot password view model`() = runTest {
        val email = "test@email.com"
        coEvery { forgotPasswordUseCase(any()) } returns flowOf(
            UseCaseResult.Success(
                ForgotPasswordResponse()
            )
        )

        forgotPasswordViewModel.forgotPassword(email)

        forgotPasswordViewModel.responseForgotPassword.test {
            assert(true)
            cancelAndIgnoreRemainingEvents()
        }
    }
}