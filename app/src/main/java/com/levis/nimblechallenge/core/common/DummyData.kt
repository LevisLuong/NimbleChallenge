package com.levis.nimblechallenge.core.common

import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import java.util.Date

val dummySurvey: SurveyModel
    get() = SurveyModel(
        id = "1",
        title = "Working from home Check-In",
        description = "We would like to know...",
        coverImageUrl = "",
        createdAt = Date()
    )