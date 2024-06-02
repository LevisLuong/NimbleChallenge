package com.levis.nimblechallenge.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.levis.nimblechallenge.core.common.BaseViewModel
import com.levis.nimblechallenge.core.common.DELAY_SPLASH_TIME
import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localDataSource: LocalDataSource,
) : BaseViewModel() {

    init {
        checkLoggedIn()
    }

    private var _navEvent = MutableSharedFlow<SplashNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    private fun checkLoggedIn() {
        viewModelScope.launch {
            delay(DELAY_SPLASH_TIME)
            val isLogged = localDataSource.isLogin()
            if (isLogged) {
                _navEvent.emit(SplashNavEvent.Home)
            } else {
                _navEvent.emit(SplashNavEvent.Login)
            }
        }
    }
}