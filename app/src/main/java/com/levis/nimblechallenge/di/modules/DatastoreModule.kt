package com.levis.nimblechallenge.di.modules

import com.levis.nimblechallenge.data.local.datastore.EncryptedSharedPreferences
import com.levis.nimblechallenge.data.local.datastore.LocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DatastoreModule {

    @Singleton
    @Binds
    abstract fun bindLocalDataSource(
        dataSource: EncryptedSharedPreferences
    ): LocalDataSource

}