package com.example.runforrun.domain.model

data class CurrentRunning(
    val distance: Int = 0,
    val speed: Float = 0.0f,
    val tracking: Boolean = false,
    val pathPoints: List<PathPoint> = emptyList()
)
