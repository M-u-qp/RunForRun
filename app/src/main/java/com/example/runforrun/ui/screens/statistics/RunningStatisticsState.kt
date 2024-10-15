package com.example.runforrun.ui.screens.statistics

import com.example.runforrun.common.extension.setFirstDayOfWeek
import com.example.runforrun.common.extension.setLastDayOfWeek
import com.example.runforrun.common.extension.setMinimumTime
import com.example.runforrun.common.extension.toCalendar
import com.example.runforrun.data.model.Run
import java.util.Calendar
import java.util.Date

data class RunningStatisticsState(
    val runList: List<Run>,
    val dateRange: ClosedRange<Date>,
    val statistic: Statistic,
    val statisticsOnDate: Map<Date, TotalStatisticsOnDate>
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

    companion object {
        val EMPTY
            get() = RunningStatisticsState(
                runList = emptyList(),
                dateRange = Calendar.getInstance().setFirstDayOfWeek().time..
                        Calendar.getInstance().setLastDayOfWeek().time,
                statistic = Statistic.DISTANCE,
                statisticsOnDate = emptyMap()
            )
    }
}

data class TotalStatisticsOnDate(
    val date: Date = Date(),
    val distance: Int = 0,
    val duration: Long = 0L,
    val caloriesBurned: Int = 0
) {
    operator fun plus(other: TotalStatisticsOnDate?) = other?.let {
        TotalStatisticsOnDate(
            date = this.date,
            distance = this.distance + other.distance,
            duration = this.duration + other.duration,
            caloriesBurned = this.caloriesBurned + other.caloriesBurned
        )
    } ?: this

    companion object {
        fun running(run: Run) = TotalStatisticsOnDate(
            date = run.timestamp.toCalendar().setMinimumTime().time,
            distance = run.distance,
            duration = run.duration,
            caloriesBurned = run.caloriesBurned
        )
    }
}