package com.levis.nimblechallenge.data.network.request

import com.google.gson.annotations.SerializedName
import com.levis.nimblechallenge.BuildConfig
import com.levis.nimblechallenge.core.common.GrantType

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("grant_type") val grantType: String = GrantType.PASSWORD,
    @SerializedName("client_id") val clientId: String = BuildConfig.CLIENT_ID,
    @SerializedName("client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET
)
