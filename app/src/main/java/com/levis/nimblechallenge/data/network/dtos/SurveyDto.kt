package com.levis.nimblechallenge.data.network.dtos

import com.google.gson.annotations.SerializedName

data class SurveyDto(
    @SerializedName("id")
    var id: String?,
    @SerializedName("active_at")
    val activeAt: String?,
    @SerializedName("cover_image_url")
    val coverImageUrl: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("inactive_at")
    val inactiveAt: Any?,
    @SerializedName("is_active")
    val isActive: Boolean?,
    @SerializedName("survey_type")
    val surveyType: String?,
    @SerializedName("thank_email_above_threshold")
    val thankEmailAboveThreshold: String?,
    @SerializedName("thank_email_below_threshold")
    val thankEmailBelowThreshold: String?,
    @SerializedName("title")
    val title: String?
)