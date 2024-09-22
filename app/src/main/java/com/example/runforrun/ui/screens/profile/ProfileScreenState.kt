package com.example.runforrun.ui.screens.profile

import com.example.runforrun.data.model.User

data class ProfileScreenState(
    val totalDistance: Float = 0.0f,
    val totalDuration: Float = 0.0f,
    val totalCaloriesBurned: Long = 0L,
    val user: User = User(),
    val editMode: Boolean = false,
    val error: String? = null
)