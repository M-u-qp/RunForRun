package com.example.runforrun.domain.model

data class CurrentRunningAndCalories(
    val currentRunning: CurrentRunning = CurrentRunning(),
    val caloriesBurned: Int = 0
)