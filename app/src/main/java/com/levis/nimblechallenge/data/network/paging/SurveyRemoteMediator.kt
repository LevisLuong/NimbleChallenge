package com.levis.nimblechallenge.data.network.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.levis.nimblechallenge.data.local.database.RoomDb
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.local.database.entity.SurveyRemoteKeyEntity
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.domain.mappers.toEntity
import com.levis.nimblechallenge.domain.mappers.toSurveyKeyEntities
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class SurveyRemoteMediator(
    private val database: RoomDb,
    private val api: Api,
) : RemoteMediator<Int, SurveyEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SurveyEntity>
    ): MediatorResult {
        return try {
            val currentPage = when (loadType) {
                LoadType.REFRESH -> 1

                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextPage ?: return MediatorResult.Success(
                        endOfPaginationReached = remoteKeys != null,
                    )
                    nextKey
                }
            }

            val response = api.getSurveyList(
                page = currentPage,
                size = state.config.pageSize
            )

            val surveyList = response.data
            val totalPages = response.meta?.pages ?: 0
            val endOfPaginationReached = surveyList.isEmpty() || currentPage >= totalPages
            val nextPage = if (endOfPaginationReached) null else currentPage + 1

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.surveyDao().clearAll()
                    database.surveyKeyDao().clearAll()
                }

                val surveyKeys = surveyList.toSurveyKeyEntities(nextPage)

                if (surveyKeys.isNotEmpty() && surveyList.isNotEmpty()) {
                    database.surveyKeyDao().upsertAll(remoteKeys = surveyKeys)
                    database.surveyDao()
                        .upsertAll(surveys = surveyList.map { it.toEntity() })
                }
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val currentTimeMillis = System.currentTimeMillis()
        val creationTime = database.surveyKeyDao().getCreationTime() ?: 0
        val shouldRefresh = currentTimeMillis - creationTime < cacheTimeout
        return if (shouldRefresh) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, SurveyEntity>
    ): SurveyRemoteKeyEntity? {
        return state.lastItemOrNull()?.let { survey ->
            database.surveyKeyDao().getById(id = survey.id)
        }
    }
}
