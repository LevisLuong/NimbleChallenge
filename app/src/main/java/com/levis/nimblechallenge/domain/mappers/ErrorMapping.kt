package com.levis.nimblechallenge.domain.mappers


import com.google.gson.Gson
import com.levis.nimblechallenge.data.network.dtos.ErrorResponse
import com.levis.nimblechallenge.domain.model.error.DataException
import kotlinx.serialization.SerializationException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.UnknownHostException

fun Throwable.mapError(): DataException {
    return when (this) {
        is ConnectException -> DataException.Network
        is UnknownHostException -> DataException.Network
        is InterruptedIOException -> DataException.Network

        is HttpException -> {
            val errorResponse = parseErrorResponse(response())
            DataException.Api(
                errorResponse,
                code(),
                message()
            )
        }

        is DataException.Api -> this

        else -> DataException.Unknown(this.message)
    }
}

@Suppress("MagicNumber")
fun <T> Response<T>.toException(): DataException {
    return when {
        code() == 404 -> DataException.NotFound
        code() == 405 -> DataException.MethodNotAllowed
        code() in 500..600 -> DataException.ServerException
        else -> {
            val errorResponse = parseErrorResponse(this)
            DataException.Api(
                errorResponse,
                code(),
                message()
            )
        }
    }
}

fun parseErrorResponse(response: Response<*>?): ErrorResponse? {
    val jsonString = response?.errorBody()?.string()
    return try {
        jsonString?.let { Gson().fromJson(it, ErrorResponse::class.java) }
    } catch (exception: IOException) {
        null
    } catch (exception: SerializationException) {
        null
    } catch (exception: IllegalStateException) {
        null
    }
}