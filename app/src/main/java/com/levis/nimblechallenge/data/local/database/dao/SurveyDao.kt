package com.levis.nimblechallenge.data.local.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity

@Dao
interface SurveyDao {
    @Upsert
    suspend fun upsertAll(surveys: List<SurveyEntity>)

    @Query("SELECT * FROM surveys")
    fun getAll(): PagingSource<Int, SurveyEntity>

    @Query("DELETE FROM surveys")
    suspend fun clearAll()
}
