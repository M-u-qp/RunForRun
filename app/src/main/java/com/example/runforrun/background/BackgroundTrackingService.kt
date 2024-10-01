package com.example.runforrun.background

import android.content.Intent
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.example.runforrun.domain.tracking.Tracking
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@AndroidEntryPoint
class BackgroundTrackingService : LifecycleService() {

    @Inject
    lateinit var tracking: Tracking

    @Inject
    lateinit var notificationHelper: NotificationHelper
    private var job: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        when (intent?.action) {
            ACTION_PAUSE -> tracking.pauseTracking()
            ACTION_RESUME -> tracking.startOrResumeTracking()
            ACTION_START -> {
                startForeground(
                    NotificationHelper.NOTIFICATION_ID,
                    notificationHelper.getNotification()
                )
                if (job == null) {
                    job = combine(
                        tracking.trackingDuration,
                        tracking.currentRunning
                    ) { duration, runState ->
                        notificationHelper.updateNotification(
                            duration = duration,
                            tracking = runState.tracking
                        )
                    }.launchIn(lifecycleScope)
                }
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        notificationHelper.removeNotification()
        job?.cancel()
        job = null
    }

    companion object {
        const val ACTION_PAUSE = "action_pause"
        const val ACTION_RESUME = "action_resume"
        const val ACTION_START = "action_start"
    }
}