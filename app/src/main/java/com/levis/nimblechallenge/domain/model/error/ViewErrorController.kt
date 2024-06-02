package com.levis.nimblechallenge.domain.model.error

fun DataException.mapToViewError(): ViewError {
    return when (this) {
        is DataException.Api -> {
            ViewError(
                title = "Error",
                message = this.error?.errors?.firstOrNull()?.detail ?: this.message
                ?: "An error occurred",
                debugMessage = this.error?.errors?.firstOrNull()?.code ?: this.message
            )
        }

        is DataException.Unknown -> {
            ViewError(
                title = "Error",
                message = "An error occurred",
                debugMessage = this.debugMessage ?: this.message
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