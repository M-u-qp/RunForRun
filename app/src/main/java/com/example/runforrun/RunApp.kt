package com.example.runforrun

import android.app.Application
import com.example.runforrun.background.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class RunApp : Application() {
    @Inject
    lateinit var notificationHelper: NotificationHelper
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        notificationHelper.createNotificationChannel()
    }
}