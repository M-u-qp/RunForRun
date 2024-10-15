package com.example.runforrun.common.utils

import java.math.RoundingMode

object ConverterUts {
    fun converterMeterToKm(value: Long) = value
        .toBigDecimal()
        .divide(1000.toBigDecimal(), 3, RoundingMode.HALF_DOWN)
        .toFloat()

    fun converterMillisToMinutes(value: Long) = value
        .toBigDecimal()
        .divide(60000.toBigDecimal(), 1, RoundingMode.HALF_DOWN)
        .toFloat()
}