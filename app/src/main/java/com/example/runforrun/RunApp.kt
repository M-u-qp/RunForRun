package com.example.runforrun

import android.app.Application
import android.content.Context
import com.example.runforrun.background.NotificationHelper
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.Locale
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

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(updateResources(base))
    }

    private fun updateResources(context: Context): Context {
        val locale = Locale.getDefault()
        Locale.setDefault(locale)
        val config = context.resources.configuration.apply {
            setLocale(locale)
        }
        return context.createConfigurationContext(config)
    }
}