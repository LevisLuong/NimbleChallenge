package com.levis.nimblechallenge.data.network

import com.levis.nimblechallenge.data.network.dtos.BaseDataResponse
import com.levis.nimblechallenge.data.network.dtos.LoginTokenDto
import com.levis.nimblechallenge.data.network.dtos.SurveyDto
import com.levis.nimblechallenge.data.network.request.LoginRequest
import com.levis.nimblechallenge.data.network.request.LogoutRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface Api {

    @POST("api/v1/oauth/token")
    suspend fun login(@Body request: LoginRequest): BaseDataResponse<LoginTokenDto>


    @GET("/api/v1/surveys")
    suspend fun getSurveyList(
        @Query("page[number]") page: Int = 1,
        @Query("page[size]") size: Int = 5,
    ): BaseDataResponse<List<SurveyDto>>

    @POST("api/v1/oauth/revoke")
    suspend fun logout(
        @Body request: LogoutRequest
    ): BaseDataResponse<Any>
}