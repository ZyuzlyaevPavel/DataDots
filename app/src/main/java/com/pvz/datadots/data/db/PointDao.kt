package com.pvz.datadots.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PointDao {
    @Insert
    suspend fun insertPoint(point: PointEntity)

    @Query("SELECT * FROM points ORDER BY x ASC")
    suspend fun getAllPoints(): List<PointEntity>

    @Query("DELETE FROM points")
    suspend fun deleteAllPoints()
}