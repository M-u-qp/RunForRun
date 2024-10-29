package com.example.runforrun.ui.screens.statistics

data class RunningStatisticsState(
    val totalDistance: Long = 0L,
    val totalDuration: Long = 0L,
    val totalCaloriesBurned: Long = 0L
) {
    enum class Statistic {
        DISTANCE,
        DURATION,
        CALORIES;

        override fun toString(): String {
            return when (this) {
                DISTANCE -> "Расстояние"
                DURATION -> "Продолжительность"
                CALORIES -> "Сожженные калории"
            }
        }
    }
}