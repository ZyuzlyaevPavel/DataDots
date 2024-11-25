package com.pvz.datadots.di.module

import com.pvz.datadots.data.remote.api.PointsApi
import com.pvz.datadots.data.repository.PointsRepositoryImpl
import com.pvz.datadots.domain.repository.PointsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePointsRepository(pointsApi: PointsApi): PointsRepository =
        PointsRepositoryImpl(pointsApi)
}
