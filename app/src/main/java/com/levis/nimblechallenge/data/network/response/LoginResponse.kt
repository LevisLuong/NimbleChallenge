package com.levis.nimblechallenge.data.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("data") val data: DataAttribute? = null,
) {
    data class DataAttribute(
        @SerializedName("attributes") val attributes: LoginTokenInfo? = null,
    )
}

data class LoginTokenInfo(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("created_at") val createdAt: Int? = null,
)
