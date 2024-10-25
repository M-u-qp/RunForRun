package com.example.runforrun.ui.screens.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow(AchievementsState())
    val state = combine(
        repository.getTotalDistance(),
        repository.getTotalCaloriesBurned(),
        _state
    ) { distance, calories, status ->
        val fDistance = String.format("%.1f", distance / 1000f)
        status.copy(
            totalDistance = fDistance,
            totalCaloriesBurned = calories
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AchievementsState()
    )
}