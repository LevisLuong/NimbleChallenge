package com.levis.nimblechallenge.data.local.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.levis.nimblechallenge.data.local.database.entity.SurveyRemoteKeyEntity

@Dao
interface SurveyRemoteKeyDao {
    @Upsert
    suspend fun upsertAll(remoteKeys: List<SurveyRemoteKeyEntity>)

    @Query("SELECT * FROM survey_remote_key WHERE id =:id")
    suspend fun getById(id: String): SurveyRemoteKeyEntity

    @Query("Select createdAt From survey_remote_key Order By createdAt DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM survey_remote_key")
    suspend fun clearAll()
}
