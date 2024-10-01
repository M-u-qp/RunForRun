package com.example.runforrun.domain.common.utils

import android.location.Location
import com.example.runforrun.domain.model.PathPoint
import kotlin.math.roundToInt

object LocationUts {
    fun getDistancePathPoints(
        pathPoint1: PathPoint,
        pathPoint2: PathPoint
    ): Int {
        return if (pathPoint1 is PathPoint.LocationPoint &&
            pathPoint2 is PathPoint.LocationPoint
        ) {
            val results = FloatArray(1)
            Location.distanceBetween(
                pathPoint1.location.latitude,
                pathPoint1.location.longitude,
                pathPoint2.location.latitude,
                pathPoint2.location.longitude,
                results
            )
            results[0].roundToInt()
        } else 0
    }

    fun calculateDistance(pathPoints: List<PathPoint>): Int {
        var distance = 0
        pathPoints.forEachIndexed { i, pathPoint ->
            if (i == pathPoints.size - 1) {
                return@forEachIndexed
            }
            distance += getDistancePathPoints(pathPoint, pathPoints[i + 1])
        }
        return distance
    }
}