package com.levis.nimblechallenge.data.network.adapter

import com.levis.nimblechallenge.BuildConfig
import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.request.RefreshTokenRequest
import dagger.Lazy
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val authService: Lazy<Api>,
    private val localDataSource: LocalDataSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenType = localDataSource.getTokenType()
        val refreshTokenUrl = BuildConfig.APIUrl + "api/v1/oauth/token"
        if (response.request.url.toUrl().toString() == refreshTokenUrl) {
            onAuthorizationError()
            return null
        }
        return try {
            localDataSource.getRefreshToken()
                .let {
                    authService.get().refreshToken(
                        RefreshTokenRequest(
                            refreshToken = it
                        )
                    ).execute().body()
                }
                ?.data
                ?.let {
                    localDataSource.saveAccessToken(it.accessToken.orEmpty())
                    localDataSource.saveRefreshToken(it.refreshToken.orEmpty())
                }
                ?.let {
                    localDataSource.getAccessToken()
                }
                ?.let {
                    response.request.newBuilder()
                        .header("Authorization", "$tokenType $it")
                        .build()
                } ?: return null
        } catch (e: Exception) {
            onAuthorizationError()
            null
        }
    }

    private fun onAuthorizationError() {
        localDataSource.removeAccessToken()
        localDataSource.removeRefreshToken()
    }
}
