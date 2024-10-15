package com.example.runforrun.ui.screens.statistics.utils

import com.example.runforrun.data.model.Run
import com.example.runforrun.ui.screens.statistics.TotalStatisticsOnDate
import java.util.Date

object StatisticsTotalization {
    fun totalizationByDate(
        list: List<Run>
    ): Map<Date, TotalStatisticsOnDate> {
        return buildMap {
            list.forEach { run ->
                val newStatistics = TotalStatisticsOnDate.running(run)
                this[newStatistics.date] = newStatistics + this[newStatistics.date]
            }
        }
    }
}