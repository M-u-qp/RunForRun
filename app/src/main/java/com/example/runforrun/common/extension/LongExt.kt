package com.example.runforrun.common.extension

import java.math.BigDecimal
import java.math.RoundingMode

fun Long.toDurationInSeconds(): Float {
    return this.toBigDecimal()
        .divide(BigDecimal(3_600_000), 2, RoundingMode.HALF_UP)
        .toFloat()
}