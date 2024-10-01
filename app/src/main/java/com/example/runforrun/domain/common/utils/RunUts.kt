package com.example.runforrun.domain.common.utils

object RunUts {
    fun calculateCaloriesBurned(distance: Int, weight: Float) =
        (0.75f * weight) * (distance / 1000f)
}