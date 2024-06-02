package com.levis.nimblechallenge.di.modules

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.levis.nimblechallenge.data.local.database.RoomDb
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.network.Api
import com.levis.nimblechallenge.data.network.paging.SurveyRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagerModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideSurveysPager(
        database: RoomDb,
        api: Api
    ): Pager<Int, SurveyEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                prefetchDistance = 0,
                initialLoadSize = 1
            ),
            remoteMediator = SurveyRemoteMediator(
                database = database,
                api = api
            ),
            pagingSourceFactory = {
                database.surveyDao().getAll()
            }
        )
    }
}
