package com.example.runforrun.ui.screens.onboard

import com.example.runforrun.data.model.Gender

interface OnBoardingScreenEvent {
    fun updateName(name: String)
    fun updateGender(gender: Gender)
    fun updateWeight(weight: Float)
    fun updateWeeklyGoal(weeklyGoal: Float)
}