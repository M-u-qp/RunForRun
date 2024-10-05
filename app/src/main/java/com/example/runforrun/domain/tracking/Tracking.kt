package com.example.runforrun.domain.tracking

import com.example.runforrun.domain.common.utils.LocationUts
import com.example.runforrun.domain.model.CurrentRunning
import com.example.runforrun.domain.model.LocationTracking
import com.example.runforrun.domain.model.PathPoint
import com.example.runforrun.domain.tracking.background.BackgroundTracking
import com.example.runforrun.domain.tracking.location.LocationMonitoring
import com.example.runforrun.domain.tracking.timer.Timer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import java.math.RoundingMode
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Tracking @Inject constructor(
    private val locationMonitoring: LocationMonitoring,
    private val timer: Timer,
    private val backgroundTracking: BackgroundTracking
) {
    private val _currentRunning = MutableStateFlow(CurrentRunning())
    val currentRunning = _currentRunning
    private var tracking = false
        set(value) {
            _currentRunning.update { it.copy(tracking = value) }
            field = value
        }

    private val _trackingDuration = MutableStateFlow(0L)
    val trackingDuration = _trackingDuration.asStateFlow()

    private val timerCallback = { timeElapsed: Long ->
        _trackingDuration.update { timeElapsed }
    }

    private val locationCallback = object : LocationMonitoring.LocationCallback {
        override fun locationUpdate(results: List<LocationTracking>) {
            if (tracking) {
                results.forEach { i ->
                    addPathPoints(i)
                    Timber.d(
                        "Новая точка местоположения: " +
                                "Широта: ${i.location.latitude}" +
                                " Долгота: ${i.location.longitude}"

                    )
                }
            }
        }
    }

    private fun addPathPoints(info: LocationTracking) {
        _currentRunning.update { state ->
            val pathPoints = state.pathPoints + PathPoint.LocationPoint(info.location)
            state.copy(
                pathPoints = pathPoints,
                distance = state.distance.run {
                    var distance = this
                    if (pathPoints.size > 1) {
                        distance += LocationUts.getDistancePathPoints(
                            pathPoint1 = pathPoints[pathPoints.size - 1],
                            pathPoint2 = pathPoints[pathPoints.size - 2]
                        )
                    }
                    distance
                },
                speed = (info.speed * 3.6f).toBigDecimal()
                    .setScale(2, RoundingMode.HALF_UP).toFloat()
            )
        }
    }

    private fun initialValue() {
        _currentRunning.update { CurrentRunning() }
        _trackingDuration.update { 0 }
    }

    private var first = true
    fun startOrResumeTracking() {
        if (tracking) {
            return
        }
        if (first) {
            initialValue()
            backgroundTracking.start()
            first = false
        }
        tracking = true
        timer.startOrResume(timerCallback)
        locationMonitoring.setCallback(locationCallback)
    }

    private fun addEmptyLine() {
        _currentRunning.update {
            it.copy(
                pathPoints = it.pathPoints + PathPoint.EmptyLocationPoint
            )
        }
    }

    fun pauseTracking() {
        tracking = false
        locationMonitoring.removeCallback()
        timer.pause()
        addEmptyLine()
    }

    fun stopTracking() {
        pauseTracking()
        backgroundTracking.stop()
        timer.stop()
        initialValue()
        first = true
    }
}