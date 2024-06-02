package com.levis.nimblechallenge.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.levis.nimblechallenge.data.local.database.RoomDb.Companion.VERSION
import com.levis.nimblechallenge.data.local.database.dao.SurveyDao
import com.levis.nimblechallenge.data.local.database.dao.SurveyRemoteKeyDao
import com.levis.nimblechallenge.data.local.database.entity.SurveyEntity
import com.levis.nimblechallenge.data.local.database.entity.SurveyRemoteKeyEntity

@Database(
    entities = [SurveyEntity::class, SurveyRemoteKeyEntity::class],
    version = VERSION,
    exportSchema = false
)
abstract class RoomDb : RoomDatabase() {
    abstract fun surveyDao(): SurveyDao
    abstract fun surveyKeyDao(): SurveyRemoteKeyDao

    companion object {
        private const val NAME = "app_db"
        const val VERSION = 1
        fun create(context: Context): RoomDb = Room.databaseBuilder(
            context = context,
            klass = RoomDb::class.java,
            name = NAME
        ).build()
    }
}