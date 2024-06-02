package com.levis.nimblechallenge.data.network.request

import com.google.gson.annotations.SerializedName
import com.levis.nimblechallenge.BuildConfig

data class ForgotPasswordRequest(
    @SerializedName("user") val user: UserForgotPassword,
    @SerializedName("client_id") val clientId: String = BuildConfig.CLIENT_ID,
    @SerializedName("client_secret") val clientSecret: String = BuildConfig.CLIENT_SECRET
) {
    data class UserForgotPassword(
        val email: String
    )
}
