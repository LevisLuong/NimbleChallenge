package com.levis.nimblechallenge.domain.model.error

data class ViewError(
    var title: String,
    var message: String,
    var debugMessage: String? = ""
)