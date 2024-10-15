package com.example.runforrun.common.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

fun Date.toCalendar(): Calendar = Calendar.getInstance().also { it.time }
fun Date.getFormattedDate(): String = SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault())
    .format(this)