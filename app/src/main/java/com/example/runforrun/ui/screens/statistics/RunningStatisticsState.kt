package com.example.runforrun.ui.screens.statistics

import android.content.Context
import com.example.runforrun.R

data class RunningStatisticsState(
    val dailyDistances: List<Long> = List(7) { 0L },
    val dailyDurations: List<Long> = List(7) { 0L },
    val dailyCalories: List<Long> = List(7) { 0L },
    val totalDistance: Long = 0L,
    val totalDuration: Long = 0L,
    val totalCaloriesBurned: Long = 0L,
    val selectedStatistic: Statistic = Statistic.DISTANCE
) {
    enum class Statistic {
        DISTANCE,
        DURATION,
        CALORIES;

        fun getDisplayName(context: Context): String {
            return when (this) {
                DISTANCE -> context.getString(R.string.distance)
                DURATION -> context.getString(R.string.duration)
                CALORIES -> context.getString(R.string.calories_burned)
            }
        }
    }
}