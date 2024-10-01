package com.example.runforrun.domain.tracking.timer

interface Timer {
    fun startOrResume(callback: (time: Long) -> Unit)
    fun stop()
    fun pause()
}