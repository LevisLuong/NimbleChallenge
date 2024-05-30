package com.levis.nimblechallenge.domain.repository

import androidx.paging.PagingData
import com.nyinyihtunlwin.domain.model.SurveyModel
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun getSurveyList(): Flow<PagingData<SurveyModel>>
    suspend fun getSurveyDetails(id: String): Flow<SurveyModel>
}
