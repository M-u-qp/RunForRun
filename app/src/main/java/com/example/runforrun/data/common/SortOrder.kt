package com.example.runforrun.data.common

enum class SortOrder {
    DATE,
    DURATION,
    DISTANCE,
    CALORIES_BURNED,
    AVG_SPEED;

    override fun toString(): String {
        return super.toString()
            .lowercase()
            .replace('_',' ')
            .replaceFirstChar { it.uppercase() }
    }
}