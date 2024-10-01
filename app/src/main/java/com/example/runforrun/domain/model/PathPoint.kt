package com.example.runforrun.domain.model

sealed interface PathPoint {
    data class LocationPoint(val location: Location) : PathPoint
    data object EmptyLocationPoint : PathPoint
}