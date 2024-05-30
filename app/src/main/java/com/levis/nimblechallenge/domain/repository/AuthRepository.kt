package com.levis.nimblechallenge.domain.repository

import com.nyinyihtunlwin.domain.model.LoginPayload
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(payload: LoginPayload): Flow<Unit>
    suspend fun logout(): Flow<Unit>
}
