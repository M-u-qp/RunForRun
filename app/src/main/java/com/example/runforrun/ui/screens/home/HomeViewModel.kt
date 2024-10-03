package com.example.runforrun.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.common.extension.setFirstDayOfWeek
import com.example.runforrun.common.extension.setLastDayOfWeek
import com.example.runforrun.data.model.Run
import com.example.runforrun.data.repository.Repository
import com.example.runforrun.data.repository.UserRepository
import com.example.runforrun.domain.tracking.Tracking
import com.example.runforrun.domain.usecases.GetCurrentRunningAndCaloriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository,
    userRepository: UserRepository,
    tracking: Tracking,
    currentRunningAndCaloriesUseCase: GetCurrentRunningAndCaloriesUseCase
) : ViewModel() {
    private val calendar = Calendar.getInstance()
    private val distanceCovered = repository.getTotalDistance(
        calendar.setFirstDayOfWeek().time,
        calendar.setLastDayOfWeek().time
    )
    private val _homeScreenState = MutableStateFlow(HomeScreenState())
    val homeScreenState = combine(
        repository.getRunByDescDate(3),
        userRepository.user,
        currentRunningAndCaloriesUseCase(),
        distanceCovered,
        _homeScreenState
    ) { runList, user, runningState, distance, state ->
        state.copy(
            user = user,
            runList = runList,
            currentRunningAndCalories = runningState,
            distanceCoveredInWeek = distance / 1000f
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        HomeScreenState()
    )

    val doesUserExist = userRepository.doesUserExist
        .stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            null
        )

    val duration = tracking.trackingDuration

    fun showRun(run: Run) {
        _homeScreenState.update { it.copy(currentRunDetails = run) }
    }

    fun deleteRun(run: Run) {
        viewModelScope.launch(Dispatchers.IO) {
            dismissDialog()
            repository.deleteRun(run)
        }
    }

    private fun dismissDialog() {
        _homeScreenState.update { it.copy(currentRunDetails = null) }
    }

}