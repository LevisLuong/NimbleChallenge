package com.levis.nimblechallenge.data.network.response

data class ForgotPasswordResponse(
    val meta: MetaForgotPassword? = null,
) {
    data class MetaForgotPassword(
        val message: String? = null,
    )
}
