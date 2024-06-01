package com.levis.nimblechallenge.data.network.request

import com.levis.nimblechallenge.BuildConfig
import com.levis.nimblechallenge.core.common.GrantType
import com.squareup.moshi.Json

data class LoginRequest(
    @Json(name="email") val email: String,
    @Json(name="password") val password: String,
    @Json(name="grant_type") val grantType: String = GrantType.PASSWORD,
    @Json(name="client_id") val clientId: String = BuildConfig.CLIENT_ID,
    @Json(name="client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET
)
