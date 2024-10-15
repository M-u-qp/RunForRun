package com.example.runforrun.ui.screens.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.common.extension.setFirstDayOfWeek
import com.example.runforrun.common.extension.setLastDayOfWeek
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.ui.screens.statistics.utils.StatisticsTotalization
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class RunningStatisticsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(RunningStatisticsState.EMPTY)
    val state = _state.asStateFlow()

    init {
        runningInDate()
    }

    private fun runningInDate() = viewModelScope.launch {
        val runList = state.value.let {
            repository.getRunningStatistics(
                fromDate = it.dateRange.start,
                toDate = it.dateRange.endInclusive
            )
        }
        withContext(Dispatchers.Default) {
            _state.update {
                it.copy(
                    runList = runList,
                    statisticsOnDate = StatisticsTotalization.totalizationByDate(runList)
                )
            }
        }
    }

    fun incrementRange() {
        _state.update {
            val todayDate = Calendar.getInstance()
            if (it.dateRange.endInclusive >= todayDate.time) return
            val nextDate = Calendar.getInstance().apply {
                time = it.dateRange.start
                add(Calendar.WEEK_OF_MONTH, 1)
            }
            it.copy(
                dateRange = nextDate.setFirstDayOfWeek().time..
                        nextDate.setLastDayOfWeek().time
            )
        }
        runningInDate()
    }

    fun decrementRange() {
        _state.update {
            val previousDate = Calendar.getInstance().apply {
                time = it.dateRange.start
                add(Calendar.WEEK_OF_MONTH, -1)
            }
            it.copy(
                dateRange = previousDate.setFirstDayOfWeek().time..
                        previousDate.setLastDayOfWeek().time
            )
        }
        runningInDate()
    }

    fun selectStatistic(statistic: RunningStatisticsState.Statistic) {
        _state.update { it.copy(statistic = statistic) }
    }

}