package com.levis.nimblechallenge.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "survey_remote_key")
class SurveyRemoteKeyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nextPage: Int?,
    val createdAt: Long?
)
