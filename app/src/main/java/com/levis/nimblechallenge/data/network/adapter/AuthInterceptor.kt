package com.nyinyihtunlwin.network

import com.nyinyihtunlwin.data.datasource.local.LocalDataSource
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor @Inject constructor(
    private val localDataSource: LocalDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        synchronized(this) {
            val tokenType = localDataSource.getTokenType()
            val token = localDataSource.getAccessToken()
            val originalRequest = chain.request()
            return if (originalRequest.headers["Authorization"].isNullOrEmpty()) {
                originalRequest.newBuilder()
                    .addHeader(
                        "Authorization",
                        "$tokenType $token"
                    )
                    .build()
            } else {
                originalRequest
            }.let { chain.proceed(request = it) }
        }
    }
}
