package com.example.runforrun.background

import android.app.Application
import android.content.Intent
import android.os.Build
import com.example.runforrun.domain.tracking.background.BackgroundTracking
import javax.inject.Inject

class BackgroundTrackingImpl @Inject constructor(
    private val context: Application
) : BackgroundTracking {
    override fun start() {
        Intent(context, BackgroundTrackingService::class.java).apply {
            action = BackgroundTrackingService.ACTION_START
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                context.startForegroundService(this)
            } else {
                context.startService(this)
            }
        }
    }

    override fun stop() {
        Intent(context, BackgroundTrackingService::class.java).apply(context::stopService)
    }
}