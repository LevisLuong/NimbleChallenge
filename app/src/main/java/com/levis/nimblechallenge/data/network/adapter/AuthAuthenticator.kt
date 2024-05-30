package com.nyinyihtunlwin.network

import com.nyinyihtunlwin.data.datasource.local.LocalDataSource
import com.nyinyihtunlwin.data.model.request.RefreshTokenRequest
import com.nyinyihtunlwin.network.service.AuthService
import dagger.Lazy
import javax.inject.Inject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

class AuthAuthenticator @Inject constructor(
    private val authService: Lazy<AuthService>,
    private val localDataSource: LocalDataSource
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val tokenType = localDataSource.getTokenType()
        val refreshTokenUrl = BuildConfig.BASE_URL + URL.API_LOGIN
        if (response.request.url.toUrl().toString() == refreshTokenUrl) {
            onAuthorizationError()
            return null
        }
        return try {
            localDataSource.getRefreshToken()
                .let {
//                    Timber.d("NbAuthenticator", "Refresh token: $it")
                    authService.get().refreshToken(
                        RefreshTokenRequest(
                            refreshToken = it
                        )
                    ).execute().body()
                }
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
//            Timber.e("NbAuthenticator", "Refresh token failed", e)
            onAuthorizationError()
            null
        }
    }

    private fun onAuthorizationError() {
//        Timber.e("NbAuthenticator", "Refresh token failed")
        localDataSource.removeAccessToken()
        localDataSource.removeRefreshToken()
    }
}
