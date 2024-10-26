package com.example.runforrun.ui.screens.achievements

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.data.repository.UserRepository
import com.example.runforrun.ui.screens.achievements.utils.Achievement
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    repository: Repository,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _runningState = MutableStateFlow(AchievementsState())
    val runningState = combine(
        repository.getTotalDistance(),
        repository.getTotalCaloriesBurned(),
        _runningState
    ) { distance, calories, status ->
        status.copy(
            totalDistance = distance / 1000f,
            totalCaloriesBurned = calories
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        AchievementsState()
    )

    private val _achievementsState = MutableStateFlow<List<Boolean>>(emptyList())
    val achievementsState = _achievementsState.asStateFlow()
    private val achievementsCache = mutableMapOf<Achievement, Boolean>()

    init {
        loadAchievements()
    }

    private fun loadAchievements() {
        viewModelScope.launch {
            val states = Achievement.entries.map { achievement ->
                achievementsCache.getOrPut(achievement) {
                    userRepository.isAchievementUnlock(achievement).first().also { isUnlocked ->
                        _achievementsState.value += isUnlocked
                    }
                }
            }
            _achievementsState.value = states
            checkAndUnlockAchievements()
        }
    }

     fun checkAndUnlockAchievements() {
        viewModelScope.launch {
            val achievementConditions = mapOf(
                Achievement.MEDAL_1 to {
                    runningState.value.totalDistance >= 10
                },
                Achievement.MEDAL to {
                    runningState.value.totalDistance >= 30
                },
                Achievement.MEDAL_STAR to {
                    runningState.value.totalDistance >= 50
                },
                Achievement.BOWL to {
                    runningState.value.totalCaloriesBurned.toFloat() >= 10000
                },
                Achievement.GOAL to {
                    achievementsCache.values.take(Achievement.entries.size - 1).all { it }
                }
            )
            for (achievement in Achievement.entries) {
                if (achievementsCache[achievement] != true) {
                    achievementConditions[achievement]?.let { condition ->
                        if (condition()) {
                            userRepository.unlockAchievement(achievement)
                            achievementsCache[achievement] = true
                        }
                    }
                }
            }
        }
    }
}