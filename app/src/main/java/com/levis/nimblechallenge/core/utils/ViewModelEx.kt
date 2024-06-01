// Package set to androidx.lifecycle so we can have access to package private methods

@file:Suppress("PackageDirectoryMismatch")

package androidx.lifecycle

import com.levis.nimblechallenge.core.common.BaseViewModel
import com.levis.nimblechallenge.core.utils.UseCaseResult
import com.levis.nimblechallenge.core.utils.onError
import com.levis.nimblechallenge.core.utils.onSuccess
import com.levis.nimblechallenge.domain.model.error.ViewError
import com.levis.nimblechallenge.domain.model.error.mapToViewError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

fun <T> T.sendViewError(viewError: ViewError) where T : BaseViewModel {
    viewModelScope.launch {
        errorMutableSharedFlow.emit(viewError)
    }
}

suspend fun <T> T.emitViewError(viewError: ViewError) where  T : BaseViewModel {
    errorMutableSharedFlow.emit(viewError)
}

val <T> T.viewErrorFlow: SharedFlow<ViewError> where  T : BaseViewModel
    get() {
        return errorMutableSharedFlow
    }


val <T> T.loadingFlow: StateFlow<Boolean> where  T : BaseViewModel
    get() {
        return loadingMutableStateFlow
    }

var <T> T.isLoading: Boolean where  T : BaseViewModel
    get() {
        return loadingMutableStateFlow.value
    }
    set(value) {
        loadingMutableStateFlow.tryEmit(value)
    }

fun <F, T> Flow<F>.bindLoading(t: T): Flow<F> where  T : BaseViewModel {
    return this
        .onStart {
            t.loadingMutableStateFlow.value = true
        }
        .onCompletion {
            t.loadingMutableStateFlow.value = false
        }
}


fun <F, T> Flow<UseCaseResult<F>>.bindError(t: T): Flow<UseCaseResult<F>> where  T : BaseViewModel {
    return this
        .onError {
            t.emitViewError(it.mapToViewError())
        }
}

inline fun <T, V> V.collectFlow(
    targetFlow: Flow<UseCaseResult<T>>,
    crossinline action: suspend (T) -> Unit
) where V : BaseViewModel {
    targetFlow
        .bindLoading(this)
        .bindError(this)
        .onSuccess {
            action(it)
        }
        .launchIn(viewModelScope)
}