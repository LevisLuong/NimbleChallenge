package com.levis.nimblechallenge.domain.usecases

import com.levis.nimblechallenge.domain.repository.SurveyRepository
import javax.inject.Inject

class SurveyListUseCase @Inject constructor(
    private val surveyRepository: SurveyRepository,
) {
    operator fun invoke() =
        surveyRepository.getSurveyList()
}