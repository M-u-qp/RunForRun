package com.example.runforrun.data.tracking.timer

import com.example.runforrun.domain.tracking.timer.Timer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

class TimerImpl @Inject constructor(
    private val scope: CoroutineScope
) : Timer {
    private var timeElapsed = 0L
    private var running = false
    private var callback: ((time: Long) -> Unit)? = null
    private var job: Job? = null

    private fun start() {
        if (job != null) {
            return
        }
        System.currentTimeMillis()
        this.job = scope.launch(Dispatchers.Default) {
            while (running && isActive) {
                callback?.invoke(timeElapsed)
                delay(1000)
                timeElapsed += 1000
            }
        }
    }
    override fun startOrResume(callback: (time: Long) -> Unit) {
        if (running) {
            return
        }
        this.callback = callback
        running = true
        start()
    }

    override fun stop() {
        pause()
        timeElapsed = 0
    }

    override fun pause() {
        running = false
        job?.cancel()
        job = null
        callback = null
    }
}