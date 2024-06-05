package com.levis.nimblechallenge.presentation.ui.login

import app.cash.turbine.test
import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.domain.usecases.LoginUseCase
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
import org.junit.Assert
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @MockK
    private lateinit var loginUseCase: LoginUseCase

    private lateinit var loginViewModel: LoginViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login emits Home event on success`() = runTest {
        val email = "test@email.com"
        val password = "password"
        val request = LoginRequest(email, password)
        coEvery { loginUseCase(request) } returns flowOf(UseCaseResult.Success(LoginTokenDto()))

        loginViewModel.login(email, password)

        loginViewModel.navEvent.test {
            Assert.assertEquals(LoginNavEvent.Home, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}