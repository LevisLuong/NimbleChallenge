package com.levis.nimblechallenge.data.network.dtos

data class ErrorResponse(
    val errors: List<ErrorData>?
)

data class ErrorData(
    val code: String?,
    val source: String?,
    val detail: String?
)
