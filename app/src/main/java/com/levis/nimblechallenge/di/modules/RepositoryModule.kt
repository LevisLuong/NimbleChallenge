package com.levis.nimblechallenge.di.modules

import com.levis.nimblechallenge.data.repository.AuthRepositoryImpl
import com.levis.nimblechallenge.data.repository.SurveyRepositoryImpl
import com.levis.nimblechallenge.domain.repository.AuthRepository
import com.levis.nimblechallenge.domain.repository.SurveyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindSurveyRepository(
        surveyRepository: SurveyRepositoryImpl
    ): SurveyRepository
}
