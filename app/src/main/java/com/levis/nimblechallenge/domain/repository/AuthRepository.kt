package com.levis.nimblechallenge.domain.repository

import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.ForgotPasswordRequest
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.response.ForgotPasswordResponse

interface AuthRepository {
    suspend fun login(payload: LoginRequest): LoginTokenDto
    suspend fun forgotPassword(payload: ForgotPasswordRequest): ForgotPasswordResponse
    suspend fun logout()
}
