package com.levis.nimblechallenge.data.network

import com.levis.nimblechallenge.data.network.request.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {
    @POST("api/v1/oauth/token")
    suspend fun login(@Body request: LoginRequest): Response<Any>
}