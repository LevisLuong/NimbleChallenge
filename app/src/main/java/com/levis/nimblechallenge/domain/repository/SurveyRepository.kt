package com.levis.nimblechallenge.domain.repository

import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    suspend fun getSurveyList(): Flow<Any>
    suspend fun getSurveyDetails(id: String): Flow<Any>
}
