package com.levis.nimblechallenge.data.network.request

import com.google.gson.annotations.SerializedName
import com.levis.nimblechallenge.BuildConfig
import com.levis.nimblechallenge.core.common.GrantType

data class LogoutRequest(
    @SerializedName("token") val token: String,
    @SerializedName("client_id") val clientId: String = BuildConfig.CLIENT_ID,
    @SerializedName("client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET
)
