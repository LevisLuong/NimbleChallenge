package com.levis.nimblechallenge.data.network.response

import com.google.gson.annotations.SerializedName
import com.levis.nimblechallenge.data.network.dtos.SurveyDto

data class SurveyListResponse(
    @SerializedName("data")
    val data: List<Data>?,
    @SerializedName("meta")
    val meta: Meta?
) {
    data class Data(
        @SerializedName("id")
        val id: String?,
        @SerializedName("type")
        val type: String?,
        @SerializedName("attributes")
        val attributes: SurveyDto?,
    )

    data class Meta(
        @SerializedName("page")
        val page: Int?,
        @SerializedName("page_size")
        val pageSize: Int?,
        @SerializedName("pages")
        val pages: Int?,
        @SerializedName("records")
        val records: Int?
    )
}