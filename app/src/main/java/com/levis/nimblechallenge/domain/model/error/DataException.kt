package com.levis.nimblechallenge.domain.model.error

import com.levis.nimblechallenge.data.network.dtos.ErrorResponse

sealed class DataException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)

    data object Network : DataException("Unable to connect. Please check connection.")
    data object NotFound : DataException("Not Found")
    data object MethodNotAllowed : DataException("Method Not Allowed")
    data object ServerException : DataException("Server Error")
    data object Unknown : DataException("Unknown Error")

    data class Api(
        val error: ErrorResponse?,
        val httpCode: Int,
        val httpMessage: String?
    ) : DataException()
}