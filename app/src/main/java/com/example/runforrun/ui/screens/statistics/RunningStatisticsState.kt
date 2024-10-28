package com.example.runforrun.ui.screens.statistics

import com.example.runforrun.data.model.Run
import java.util.Date

data class RunningStatisticsState(
    val runList: List<Run>,
    val dateRange: ClosedRange<Date>,
    val statistic: Statistic,
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