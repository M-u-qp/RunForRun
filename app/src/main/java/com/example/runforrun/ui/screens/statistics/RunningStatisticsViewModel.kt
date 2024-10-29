package com.example.runforrun.ui.screens.statistics

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.R
import com.example.runforrun.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RunningStatisticsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(RunningStatisticsState())
    val state = _state.asStateFlow()

    private var currentWeekStart: Date = getStartOfWeek(Date())
    private var currentWeekEnd: Date = getEndOfWeek(Date())

    init {
        loadCurrentWeekStatistics()
    }

    private fun loadCurrentWeekStatistics() {
        viewModelScope.launch {
            val distance = repository.getTotalDistance(currentWeekStart, currentWeekEnd).first()
            val duration = repository.getTotalDuration(currentWeekStart, currentWeekEnd).first()
            val calories =
                repository.getTotalCaloriesBurned(currentWeekStart, currentWeekEnd).first()
            _state.value = RunningStatisticsState(
                totalDistance = distance,
                totalDuration = duration,
                totalCaloriesBurned = calories
            )
        }
    }

    fun switchToPreviousWeek(context: Context) {
        val previousWeekStart = Date(currentWeekStart.time - 7 * 24 * 60 * 60 * 1000)
        val previousWeekEnd = Date(currentWeekEnd.time - 7 * 24 * 60 * 60 * 1000)
        val year = getDataOfCurrentYear(currentWeekStart)
        viewModelScope.launch {
            val totalDistanceThisYear = repository.getTotalDistance(year, currentWeekStart).first()
            if (totalDistanceThisYear > 0) {
                currentWeekStart = previousWeekStart
                currentWeekEnd = previousWeekEnd
                loadCurrentWeekStatistics()
            } else {
                val toastText = context.getString(R.string.no_more_runs_this_year)
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun switchToNextWeek() {
        val nextWeekStart = Date(currentWeekStart.time + 7 * 24 * 60 * 60 * 1000)
        val nextWeekEnd = Date(currentWeekEnd.time + 7 * 24 * 60 * 60 * 1000)
        if (nextWeekStart <= Date()) {
            currentWeekStart = nextWeekStart
            currentWeekEnd = nextWeekEnd
            loadCurrentWeekStatistics()
        }
    }

    private fun getStartOfWeek(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    private fun getEndOfWeek(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
            set(Calendar.HOUR_OF_DAY, 23)
            set(Calendar.MINUTE, 59)
            set(Calendar.SECOND, 59)
            set(Calendar.MILLISECOND, 999)
        }
        return calendar.time
    }

    private fun getDataOfCurrentYear(date: Date): Date {
        val calendar = Calendar.getInstance().apply {
            time = date
            set(Calendar.MONTH, Calendar.JANUARY)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.time
    }

    fun getCurrentWeekLabel(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val start = dateFormat.format(currentWeekStart)
        val end = dateFormat.format(currentWeekEnd)
        return "$start - $end"
    }
}