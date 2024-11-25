package com.pvz.datadots.data.repository

import com.pvz.datadots.data.remote.api.PointResponse
import com.pvz.datadots.data.remote.api.PointsApi
import com.pvz.datadots.domain.repository.PointsRepository
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PointsRepositoryImpl @Inject constructor(
    private val pointsApi: PointsApi
) : PointsRepository {

    override suspend fun fetchPoints(count: Int): Response<PointResponse> {
        return pointsApi.getPoints(count)
    }
}
