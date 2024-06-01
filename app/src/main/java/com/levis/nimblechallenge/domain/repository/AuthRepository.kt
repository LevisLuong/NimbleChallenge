package com.levis.nimblechallenge.domain.repository

import com.levis.nimblechallenge.data.network.request.LoginRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(payload: LoginRequest): Any
    suspend fun logout(): Flow<Unit>
}
