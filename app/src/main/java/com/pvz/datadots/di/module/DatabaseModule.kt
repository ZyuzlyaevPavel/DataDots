package com.pvz.datadots.di.module

import android.content.Context
import com.pvz.datadots.data.db.DataDotsDatabase
import com.pvz.datadots.data.db.PointDao
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
        fun provideDatabase(@ApplicationContext context: Context): DataDotsDatabase {
            return DataDotsDatabase.getDatabase(context)
        }

        @Provides
        fun providePointDao(database: DataDotsDatabase): PointDao {
            return database.pointDao()
        }


}