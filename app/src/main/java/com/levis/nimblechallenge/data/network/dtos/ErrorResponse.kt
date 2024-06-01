package com.levis.nimblechallenge.data.network.dtos

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ErrorResponse(
    @Json(name = "errors") val errors: List<ErrorData>?
)

@JsonClass(generateAdapter = true)
data class ErrorData(
    @Json(name = "code")
    val code: String?,
    @Json(name = "source")
    val source: String?,
    @Json(name = "detail")
    val detail: String?
)
