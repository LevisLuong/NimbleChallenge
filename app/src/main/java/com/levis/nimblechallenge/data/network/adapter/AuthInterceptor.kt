package com.levis.nimblechallenge.data.network.adapter

import com.levis.nimblechallenge.data.local.datastore.EncryptedSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val encryptedPrefsDatastore: EncryptedSharedPreferences,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", getAuthToken())
            .build()
        return chain.proceed(request)
    }

    private fun getAuthToken(): String {
        val tokenType = encryptedPrefsDatastore.getTokenType()
        val accessToken = encryptedPrefsDatastore.getAccessToken()
        return "$tokenType $accessToken"
    }
}