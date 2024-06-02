package com.levis.nimblechallenge.di.modules

import android.content.Context
import com.levis.nimblechallenge.data.local.database.RoomDb
import com.levis.nimblechallenge.data.local.database.dao.SurveyDao
import com.levis.nimblechallenge.data.local.database.dao.SurveyRemoteKeyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): RoomDb =
        RoomDb.create(context = appContext)

    @Provides
    fun provideSurveyDao(
        database: RoomDb
    ): SurveyDao = database.surveyDao()

    @Provides
    fun provideSurveyRemoteKeyDao(
        database: RoomDb
    ): SurveyRemoteKeyDao = database.surveyKeyDao()
}