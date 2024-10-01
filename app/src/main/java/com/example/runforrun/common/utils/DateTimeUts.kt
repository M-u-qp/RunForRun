package com.example.runforrun.common.utils

import java.util.concurrent.TimeUnit

object DateTimeUts {
    fun getFormattedTime(ms: Long, isMillis: Boolean = false): String {
        var milliseconds = ms
        val hours = TimeUnit.MILLISECONDS.toHours(ms)
        milliseconds -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds)
        milliseconds -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds)
        val formattedString = "${if (hours < 10) "0" else ""}$hours" +
                "${if (minutes < 10) "0" else ""}$minutes" +
                "${if (seconds < 10) "0" else ""}$seconds"
        return if (!isMillis) {
            formattedString
        } else {
            milliseconds -= TimeUnit.SECONDS.toMillis(seconds)
            milliseconds /= 10
            formattedString + ":" +
                    "${if (milliseconds < 10) "0" else ""}$milliseconds"
        }
    }
}