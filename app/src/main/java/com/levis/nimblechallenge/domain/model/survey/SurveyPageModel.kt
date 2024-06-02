package com.levis.nimblechallenge.domain.model.survey

data class SurveyPageModel(
    val surveyList: List<SurveyModel>,
    val page: Int,
    val totalPages: Int,
)