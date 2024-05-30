package com.levis.nimblechallenge.data.repository

import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.domain.repository.SurveyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SurveyRepositoryImpl @Inject constructor(
    private val api: Api
) : SurveyRepository {
    override suspend fun getSurveyList(): Flow<Any> {
        TODO("Not yet implemented")
    }

    override suspend fun getSurveyDetails(id: String): Flow<Any> {
        TODO("Not yet implemented")
    }

}