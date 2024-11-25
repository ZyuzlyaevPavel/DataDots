package com.pvz.datadots.domain.repository

import com.pvz.datadots.data.remote.api.PointResponse
import retrofit2.Response

interface PointsRepository {
    suspend fun fetchPoints(count: Int): Response<PointResponse>
}