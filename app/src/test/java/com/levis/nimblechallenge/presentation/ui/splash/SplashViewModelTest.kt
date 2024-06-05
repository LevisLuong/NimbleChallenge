package com.levis.nimblechallenge.presentation.ui.splash

import app.cash.turbine.test
import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
class SplashViewModelTest {

    @MockK
    private lateinit var localDataSource: LocalDataSource

    private lateinit var splashViewModel: SplashViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        splashViewModel = SplashViewModel(localDataSource)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `checkLoggedIn emits Home event when user is logged in`() = runTest {
        coEvery { localDataSource.isLogin() } returns true

        splashViewModel.navEvent.test {
            advanceUntilIdle()

            assertEquals(SplashNavEvent.Home, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `checkLoggedIn emits Login event when user is not logged in`() = runTest {
        coEvery { localDataSource.isLogin() } returns false

        splashViewModel.navEvent.test {
            advanceUntilIdle()

            assertEquals(SplashNavEvent.Login, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}