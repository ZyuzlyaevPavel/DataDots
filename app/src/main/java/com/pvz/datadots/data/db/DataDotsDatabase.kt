package com.pvz.datadots.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [PointEntity::class], version = 1)
abstract class DataDotsDatabase : RoomDatabase() {

    abstract fun pointDao(): PointDao

    companion object {
        @Volatile
        private var INSTANCE: DataDotsDatabase? = null

        fun getDatabase(context: Context): DataDotsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DataDotsDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
