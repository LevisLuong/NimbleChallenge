package com.levis.nimblechallenge.data.repository

import com.levis.nimblechallenge.core.utils.repoCall
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api
) : AuthRepository {

    override suspend fun login(payload: LoginRequest) = repoCall {
        api.login(payload)
    }

    override suspend fun logout(): Flow<Unit> {
        TODO("Not yet implemented")
    }

}