package com.levis.nimblechallenge.presentation.ui.forgotpassword

import androidx.lifecycle.collectFlow
import com.levis.nimblechallenge.core.common.BaseViewModel
import com.levis.nimblechallenge.data.network.request.ForgotPasswordRequest
import com.levis.nimblechallenge.domain.usecases.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : BaseViewModel() {

    private var _responseForgotPassword = MutableStateFlow(false)
    val responseForgotPassword = _responseForgotPassword.asStateFlow()

    fun forgotPassword(email: String) {
        collectFlow(
            forgotPasswordUseCase(
                ForgotPasswordRequest(
                    user = ForgotPasswordRequest.UserForgotPassword(
                        email
                    )
                )
            )
        ) {
            _responseForgotPassword.emit(true)
        }
    }

}