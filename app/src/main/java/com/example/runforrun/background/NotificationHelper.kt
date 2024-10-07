package com.example.runforrun.background

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.example.runforrun.R
import com.example.runforrun.common.utils.FormatterUts.getFormattedTime
import com.example.runforrun.ui.MainActivity
import com.example.runforrun.ui.navgraph.Route
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val context: Application
) {
    private val intentToRunScreen = TaskStackBuilder.create(context).run {
        addNextIntentWithParentStack(
            Intent(
                Intent.ACTION_VIEW,
                Route.CurrentRun.currentRunUri.toUri(),
                context,
                MainActivity::class.java
            )
        )
        getPendingIntent(
            0, PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE
        )!!
    }
    private val notificationBuilder
        get() = NotificationCompat.Builder(
            context,
            NOTIFICATION_CHANNEL_ID
        )
            .setSmallIcon(R.drawable.go_run)
            .setAutoCancel(false)
            .setOngoing(true)
            .setContentTitle("Время бега")
            .setContentText("00:00:00")
            .setContentIntent(intentToRunScreen)
    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    fun updateNotification(duration: Long, tracking: Boolean) {
        val notification = notificationBuilder
            .setContentText(getFormattedTime(duration))
            .clearActions()
            .addAction(getNotificationAction(tracking))
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun getNotificationAction(tracking: Boolean): NotificationCompat.Action {
        return NotificationCompat.Action(
            if (tracking) {
                R.drawable.pause
            } else {
                R.drawable.play
            },
            if (tracking) {
                "Пауза"
            } else {
                "Возобновить"
            },
            PendingIntent.getService(
                context,
                2234,
                Intent(
                    context,
                    BackgroundTrackingService::class.java
                ).apply {
                    action = if (tracking) {
                        BackgroundTrackingService.ACTION_PAUSE
                    } else {
                        BackgroundTrackingService.ACTION_RESUME
                    }
                },
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
        )
    }
    fun removeNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
    }
    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O_MR1) {
            return
        }
        val notificationChannel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(notificationChannel)
    }
    fun getNotification() = notificationBuilder.build()

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification_channel_id"
        private const val NOTIFICATION_CHANNEL_NAME = "notification_channel_name"
        const val NOTIFICATION_ID = 3
    }
}