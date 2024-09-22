package com.example.runforrun.data.model

import android.net.Uri

data class User(
    val name: String = "",
    val gender: Gender = Gender.OTHER,
    val weight: Float = 0.0f,
    val weeklyGoal: Float = 0.0f,
    val img: Uri? = null
)

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}