package com.levis.nimblechallenge.domain.repository

import androidx.paging.PagingData
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun getSurveyList(): Flow<PagingData<SurveyModel>>
    fun getSurveyDetails(id: String): Flow<Any>
}
