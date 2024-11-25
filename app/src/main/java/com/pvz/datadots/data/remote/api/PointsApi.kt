package com.pvz.datadots.data.remote.api

import com.pvz.datadots.domain.model.Point
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PointsApi {
    @GET("api/test/points")
    suspend fun getPoints(@Query("count") count: Int): Response<PointResponse>
}

data class PointResponse(
    val points: List<Point>
)


