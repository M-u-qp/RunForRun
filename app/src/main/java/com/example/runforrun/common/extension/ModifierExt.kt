package com.example.runforrun.common.extension

import androidx.compose.ui.Modifier

fun Modifier.condition(
    condition: Boolean,
    falsity: () -> Modifier = { Modifier },
    truth: () -> Modifier
): Modifier = run {
    if (condition) {
        then(truth())
    } else {
        then(falsity())
    }
}