package com.levis.nimblechallenge.presentation.ui.login

import androidx.lifecycle.collectFlow
import com.levis.nimblechallenge.core.common.BaseViewModel
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.domain.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {

    private var _navEvent = MutableSharedFlow<LoginNavEvent>()
    val navEvent = _navEvent.asSharedFlow()

    fun login(email: String, password: String) {
        collectFlow(
            loginUseCase(LoginRequest(email, password))
        ) {
            _navEvent.emit(LoginNavEvent.Home)
        }
//        loginUseCase(LoginRequest(email, password))
//            .bindLoading(this)
//            .bindError(this)
//            .onSuccess {
//                _navEvent.emit(LoginNavEvent.Home)
//            }
//            .launchIn(viewModelScope)
    }

}