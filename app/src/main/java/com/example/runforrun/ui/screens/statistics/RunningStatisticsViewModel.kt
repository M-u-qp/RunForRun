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
import java.math.RoundingMode
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

    //Загрузка из БД данных за текущую неделю
    private fun loadCurrentWeekStatistics() {
        viewModelScope.launch {
            val distances = mutableListOf<Float>()
            val durations = mutableListOf<Float>()
            val calories = mutableListOf<Float>()

            for (i in 0 until 7) {
                val dayStart = Date(currentWeekStart.time + i * 24 * 60 * 60 * 1000)
                val dayEnd = Date(dayStart.time + 24 * 60 * 60 * 1000 - 1)

                distances.add(repository.getTotalDistance(dayStart, dayEnd).first() / 1000f)
                durations.add(repository.getTotalDuration(dayStart, dayEnd).first().toBigDecimal()
                    .divide((3_600_000).toBigDecimal(), 2, RoundingMode.HALF_UP)
                    .toFloat())
                calories.add(repository.getTotalCaloriesBurned(dayStart, dayEnd).first().toFloat())
            }

            _state.value = RunningStatisticsState(
                dailyDistances = distances,
                dailyDurations = durations,
                dailyCalories = calories,
                totalDistance = distances.sum(),
                totalDuration = durations.sum(),
                totalCaloriesBurned = calories.sum()
            )
        }
    }

    //Выбор категории статистики(расстояние, длительность, сожженные калории)
    fun selectStatistic(statistic: RunningStatisticsState.Statistic) {
        _state.value = _state.value.copy(selectedStatistic = statistic)
    }

    //Смена на предыдущую неделю для графика
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

    //Смена на следующую неделю для графика
    fun switchToNextWeek() {
        val nextWeekStart = Date(currentWeekStart.time + 7 * 24 * 60 * 60 * 1000)
        val nextWeekEnd = Date(currentWeekEnd.time + 7 * 24 * 60 * 60 * 1000)
        if (nextWeekStart <= Date()) {
            currentWeekStart = nextWeekStart
            currentWeekEnd = nextWeekEnd
            loadCurrentWeekStatistics()
        }
    }

    //Начало недели
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

    //Конец недели
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

    //Текущий год
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

    //Получение диапазона дат текущей недели для показа
    fun getCurrentWeekLabel(): String {
        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val start = dateFormat.format(currentWeekStart)
        val end = dateFormat.format(currentWeekEnd)
        return "$start - $end"
    }
}