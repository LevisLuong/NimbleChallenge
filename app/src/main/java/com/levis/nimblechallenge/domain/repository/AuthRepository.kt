package com.levis.nimblechallenge.domain.repository

import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.response.LoginTokenInfo
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(payload: LoginRequest): LoginTokenInfo?
    suspend fun logout(): Flow<Unit>
}
