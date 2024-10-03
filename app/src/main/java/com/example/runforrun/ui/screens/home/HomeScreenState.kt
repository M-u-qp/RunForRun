package com.example.runforrun.ui.screens.home

import com.example.runforrun.data.model.Run
import com.example.runforrun.data.model.User
import com.example.runforrun.domain.model.CurrentRunningAndCalories

data class HomeScreenState(
    val user: User = User(),
    val runList: List<Run> = emptyList(),
    val currentRunDetails: Run? = null,
    val distanceCoveredInWeek: Float = 0.0f,
    val currentRunningAndCalories: CurrentRunningAndCalories = CurrentRunningAndCalories()
)