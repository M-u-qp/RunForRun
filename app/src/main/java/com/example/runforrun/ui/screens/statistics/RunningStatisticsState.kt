package com.example.runforrun.ui.screens.statistics

import android.content.Context
import com.example.runforrun.R

data class RunningStatisticsState(
    val dailyDistances: List<Float> = List(7) { 0f },
    val dailyDurations: List<Float> = List(7) { 0f },
    val dailyCalories: List<Float> = List(7) { 0f },
    val totalDistance: Float = 0f,
    val totalDuration: Float = 0f,
    val totalCaloriesBurned: Float = 0f,
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