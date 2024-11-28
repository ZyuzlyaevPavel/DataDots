package com.pvz.datadots.domain.repository

import com.pvz.datadots.data.db.PointEntity
import com.pvz.datadots.data.remote.api.PointResponse
import com.pvz.datadots.domain.model.Point
import retrofit2.Response

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Response<PointResponse>
    suspend fun insertPoints(points: List<Point>)
    suspend fun getAllPoints(): List<Point>
    suspend fun deleteAllPoints()
}