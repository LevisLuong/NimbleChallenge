package com.levis.nimblechallenge.data.repository

import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.request.LogoutRequest
import com.levis.nimblechallenge.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api,
    private val localDataSource: LocalDataSource,
) : AuthRepository {

    override suspend fun login(payload: LoginRequest): LoginTokenDto =
        api.login(payload)
            .data
            .also {
                localDataSource.saveAccessToken(it.accessToken.orEmpty())
                localDataSource.saveTokenType(it.tokenType.orEmpty())
                localDataSource.saveRefreshToken(it.refreshToken.orEmpty())
            }


    override suspend fun logout() {
        val tokenType = localDataSource.getTokenType()
        val accessToken = localDataSource.getAccessToken()
        api.logout(
            LogoutRequest(
                token = "$tokenType $accessToken"
            )
        )
        localDataSource.clearAll()
    }
}