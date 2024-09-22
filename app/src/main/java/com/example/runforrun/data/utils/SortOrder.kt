package com.example.runforrun.data.utils

enum class SortOrder {
    DATE,
    DISTANCE,
    DURATION,
    CALORIES_BURNED,
    AVG_SPEED;

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replace('_', ' ')
            .replaceFirstChar { it.uppercase() }
    }
}