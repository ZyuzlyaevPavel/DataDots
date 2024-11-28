package com.pvz.datadots.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "points")
data class PointEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val x: Float,
    val y: Float
)