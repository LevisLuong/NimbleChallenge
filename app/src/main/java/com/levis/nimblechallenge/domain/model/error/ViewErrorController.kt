package com.levis.nimblechallenge.domain.model.error

fun DataException.mapToViewError(): ViewError {
    return when (this) {
        is DataException.Api -> {
            ViewError(
                title = "Error",
                message = this.error?.errors?.firstOrNull()?.detail ?: this.message
                ?: "An error occurred",
                debugMessage = this.error?.errors?.firstOrNull()?.code ?: this.httpMessage
            )
        }

        else -> {
            ViewError(
                title = "Error",
                message = this.message ?: "",
                debugMessage = this.message
            )
        }
    }
}