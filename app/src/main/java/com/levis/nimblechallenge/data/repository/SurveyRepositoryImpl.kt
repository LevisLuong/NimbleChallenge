package com.levis.nimblechallenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.domain.mappers.toModel
import com.levis.nimblechallenge.domain.model.survey.SurveyModel
import com.levis.nimblechallenge.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val api: Api,
    private val pager: Pager<Int, SurveyEntity>
) : SurveyRepository {
    override fun getSurveyList(): Flow<PagingData<SurveyModel>> =
        pager.flow.map { surveyPagingData ->
            surveyPagingData.map {
                it.toModel()
            }
        }

    override fun getSurveyDetails(id: String): Flow<Any> {
        TODO("Not yet implemented")
    }

}