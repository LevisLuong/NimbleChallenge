package com.levis.nimblechallenge.domain.repository

import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.LoginRequest

interface AuthRepository {
    suspend fun login(payload: LoginRequest): LoginTokenDto
    suspend fun logout()
}
