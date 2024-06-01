package com.levis.nimblechallenge.core.utils

import com.levis.nimblechallenge.domain.mappers.mapError
import com.levis.nimblechallenge.domain.model.error.DataException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.transform

sealed class UseCaseResult<out T> {
    data class Success<T>(val value: T) : UseCaseResult<T>()
    data class Error(val exception: DataException) : UseCaseResult<Nothing>()
}

suspend inline fun <T> safeUseCase(
    crossinline block: suspend () -> T,
): UseCaseResult<T> = try {
    UseCaseResult.Success(block())
} catch (e: DataException) {
    UseCaseResult.Error(e.mapError())
} catch (e: Exception) {
    UseCaseResult.Error(DataException.Unknown)
}

@Suppress("TooGenericExceptionCaught")
inline fun <T> useCaseFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> T,
): Flow<UseCaseResult<T>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: DataException) {
        emit(UseCaseResult.Error(e.mapError()))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(DataException.Unknown))
    }
}.flowOn(coroutineDispatcher)

@Suppress("TooGenericExceptionCaught")
inline fun useCaseWithoutBodyFlow(
    coroutineDispatcher: CoroutineDispatcher,
    crossinline block: suspend () -> Unit,
): Flow<UseCaseResult<Unit>> = flow {
    try {
        val repoResult = block()
        emit(UseCaseResult.Success(repoResult))
    } catch (e: DataException) {
        emit(UseCaseResult.Error(e.mapError()))
    } catch (e: Exception) {
        emit(UseCaseResult.Error(DataException.Unknown))
    }
}.flowOn(coroutineDispatcher)

fun <T> Flow<UseCaseResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Success<T>) {
            action(result.value)
        }
        return@transform emit(result)
    }

fun <T> Flow<UseCaseResult<T>>.onError(action: suspend (DataException) -> Unit): Flow<UseCaseResult<T>> =
    transform { result ->
        if (result is UseCaseResult.Error) {
            action(result.exception)
        }
        return@transform emit(result)
    }