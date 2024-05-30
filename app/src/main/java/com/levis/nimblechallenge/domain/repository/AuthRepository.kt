package com.levis.nimblechallenge.domain.repository

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(payload: Any): Flow<Unit>
    suspend fun logout(): Flow<Unit>
}
