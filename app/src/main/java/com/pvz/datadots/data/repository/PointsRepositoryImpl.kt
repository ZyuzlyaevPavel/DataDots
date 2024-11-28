package com.pvz.datadots.data.repository

import com.pvz.datadots.data.db.PointDao
import com.pvz.datadots.data.db.PointEntity
import com.pvz.datadots.data.remote.api.PointResponse
import com.pvz.datadots.data.remote.api.PointsApi
import com.pvz.datadots.domain.repository.PointsRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi,
    private val pointDao: PointDao,

    ) : PointsRepository {

    override suspend fun fetchPoints(count: Int): Response<PointResponse> {
        return pointsApi.getPoints(count)
    }

    override suspend fun insertPoint(point: PointEntity) = pointDao.insertPoint(point)
    override suspend fun getAllPoints(): List<PointEntity> = pointDao.getAllPoints()
    override suspend fun deleteAllPoints() = pointDao.deleteAllPoints()

}
