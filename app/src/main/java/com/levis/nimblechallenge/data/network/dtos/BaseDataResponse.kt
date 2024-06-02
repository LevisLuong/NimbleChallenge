package com.levis.nimblechallenge.data.network.dtos

data class BaseDataResponse<T>(
    val data: T,
    val meta: Meta? = null
)
