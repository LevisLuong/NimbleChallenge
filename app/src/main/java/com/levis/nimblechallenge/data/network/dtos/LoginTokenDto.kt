package com.levis.nimblechallenge.data.network.dtos

import com.google.gson.annotations.SerializedName

data class LoginTokenDto(
    @SerializedName("access_token") val accessToken: String? = null,
    @SerializedName("token_type") val tokenType: String? = null,
    @SerializedName("expires_in") val expiresIn: Int? = null,
    @SerializedName("refresh_token") val refreshToken: String? = null,
    @SerializedName("created_at") val createdAt: Int? = null,
)