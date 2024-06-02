package com.levis.nimblechallenge.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "surveys")
class SurveyEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val title: String,
    val description: String,
    val coverImageUrl: String,
    val createdAt: String
)
