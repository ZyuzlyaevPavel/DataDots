package com.pvz.datadots.data.remote.api

import com.pvz.datadots.domain.model.Point
import kotlinx.coroutines.delay
import retrofit2.Response
import kotlin.random.Random

class FakePointsApi : PointsApi {
    override suspend fun getPoints(count: Int): Response<PointResponse> {
        delay(500)
        return Response.success(PointResponse(List(count) {
            Point(x = Random.nextFloat() * 100, y = Random.nextFloat() * 100)
        }))
    }
}
