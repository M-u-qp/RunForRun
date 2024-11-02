package com.example.runforrun.ui.screens.statistics.components

import android.icu.text.DateFormatSymbols
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Locale

internal val padding = 8.dp
internal val paddingMod = Modifier.padding(padding)
internal object DaysOfWeek {
    fun getDaysOfWeek(): List<String> {
        val locale = Locale.getDefault()
        val shortWeekdays = DateFormatSymbols(locale).shortWeekdays
        return listOf(
            *shortWeekdays.copyOfRange(2,8),
            shortWeekdays[1]
        )
    }
}