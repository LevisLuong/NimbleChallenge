package com.levis.nimblechallenge.domain.model.survey

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class SurveyModel(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val createdAt: Date?
) : Parcelable {

    @IgnoredOnParcel
    val largeCoverImageUrl = "${coverImageUrl}l"
}
