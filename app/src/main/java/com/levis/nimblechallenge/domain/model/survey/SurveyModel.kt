package com.levis.nimblechallenge.domain.model.survey

import java.util.Date

data class SurveyModel(
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val createdAt: Date?
) {
    val largeCoverImageUrl = "${coverImageUrl}l"
}
