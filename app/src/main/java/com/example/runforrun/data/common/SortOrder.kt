package com.example.runforrun.data.common

enum class SortOrder {
    DATE,
    DURATION,
    DISTANCE,
    CALORIES_BURNED,
    AVG_SPEED;

    override fun toString(): String {
        return when (this) {
            DATE -> "Дата"
            DURATION -> "Продолжительность"
            DISTANCE -> "Расстояние"
            CALORIES_BURNED -> "Сожженные калории"
            AVG_SPEED -> "Средняя скорость"
        }
    }
}