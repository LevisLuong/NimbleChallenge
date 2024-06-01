package com.levis.nimblechallenge.core.utils

import com.levis.nimblechallenge.domain.mappers.toException
import retrofit2.Response

inline fun <T> repoCall(
    block: () -> Response<T>
): T {
    val response = block()
    val body = response.body()
    return when (response.isSuccessful && body != null) {
        true -> body
        false -> throw response.toException()
    }
}

inline fun <T, R> Response<T>.mapSuccess(
    crossinline block: (T) -> R
): R {
    val safeBody = body()
    if (this.isSuccessful && safeBody != null) {
        return block(safeBody)
    } else {
        throw toException()
    }
}