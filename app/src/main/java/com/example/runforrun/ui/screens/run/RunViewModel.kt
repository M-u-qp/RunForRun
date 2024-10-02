package com.example.runforrun.ui.screens.run

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.model.Run
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.domain.model.CurrentRunningAndCalories
import com.example.runforrun.domain.tracking.Tracking
import com.example.runforrun.domain.usecases.GetCurrentRunningAndCaloriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.math.RoundingMode
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class RunViewModel @Inject constructor(
    private val tracking: Tracking,
    private val repository: Repository,
    getCurrentRunningAndCaloriesUseCase: GetCurrentRunningAndCaloriesUseCase
) : ViewModel() {
    val runningDuration = tracking.trackingDuration
    val runStateAndCalories = getCurrentRunningAndCaloriesUseCase()
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            CurrentRunningAndCalories()
        )

    fun playOrPauseRun() {
        if (runStateAndCalories.value.currentRunning.tracking) {
            tracking.pauseTracking()
        } else {
            tracking.startOrResumeTracking()
        }
    }

    fun finishRun(bitmap: Bitmap) {
        tracking.pauseTracking()
        saveRun(
            Run(
                image = bitmap,
                distance = runStateAndCalories.value.currentRunning.distance,
                duration = runningDuration.value,
                caloriesBurned = runStateAndCalories.value.caloriesBurned,
                avgSpeed = runStateAndCalories.value.currentRunning.distance
                    .toBigDecimal()
                    .multiply(3600.toBigDecimal())
                    .divide(runningDuration.value.toBigDecimal(), 2, RoundingMode.HALF_UP)
                    .toFloat(),
                timestamp = Date()
            )
        )
        tracking.stopTracking()
    }

    private fun saveRun(run: Run) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRun(run)
    }
}