package com.levis.nimblechallenge.data.repository

import com.levis.nimblechallenge.core.utils.mapSuccess
import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.response.LoginTokenInfo
import com.levis.nimblechallenge.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: Api,
    private val localDataSource: LocalDataSource,
) : AuthRepository {

    override suspend fun login(payload: LoginRequest): LoginTokenInfo? =
         api.login(payload)
            .mapSuccess {
                it.data?.attributes
            }.also {
                localDataSource.saveAccessToken(it?.accessToken.orEmpty())
                localDataSource.saveTokenType(it?.tokenType.orEmpty())
                localDataSource.saveRefreshToken(it?.refreshToken.orEmpty())
            }


    override suspend fun logout(): Flow<Unit> {
        TODO("Not yet implemented")
    }

}