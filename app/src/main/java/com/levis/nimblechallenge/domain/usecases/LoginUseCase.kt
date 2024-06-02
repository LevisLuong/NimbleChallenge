package com.levis.nimblechallenge.domain.usecases

import android.util.Log
import com.levis.nimblechallenge.core.utils.useCaseFlow
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.di.qualifiers.IoDispatcher
import com.levis.nimblechallenge.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: AuthRepository,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
) {

    fun login(loginRequest: LoginRequest) =
        useCaseFlow(coroutineDispatcher) {
            val response = userRepository.login(loginRequest)
            Log.d("LoginUseCase", "login info: ${response}")
        }
}