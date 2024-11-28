package com.pvz.datadots.data.mapper

import com.pvz.datadots.data.db.PointEntity
import com.pvz.datadots.domain.model.Point

object PointMapper {

    fun fromEntityToDomain(entity: PointEntity): Point {
        return Point(
            x = entity.x,
            y = entity.y
        )
    }

    fun fromDomainToEntity(domain: Point): PointEntity {
        return PointEntity(
            x = domain.x,
            y = domain.y
        )
    }

    fun fromEntitiesToDomainList(entities: List<PointEntity>): List<Point> {
        return entities.map { fromEntityToDomain(it) }
    }

    fun fromDomainListToEntities(domains: List<Point>): List<PointEntity> {
        return domains.map { fromDomainToEntity(it) }
    }
}
