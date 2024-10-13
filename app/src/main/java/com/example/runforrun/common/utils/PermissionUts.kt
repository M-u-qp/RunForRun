package com.example.runforrun.common.utils

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object PermissionUts {
    val locationPermissions = mutableListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            add(Manifest.permission.FOREGROUND_SERVICE_LOCATION)
        }
    }.toTypedArray()

    val mediaPermissions = mutableListOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    ).apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(Manifest.permission.READ_MEDIA_IMAGES)
            add(Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
        }
    }.toTypedArray()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    val notificationPermission = Manifest.permission.POST_NOTIFICATIONS
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    val allPermissions = mutableListOf<String>().apply {
        addAll(mediaPermissions)
        addAll(locationPermissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            add(notificationPermission)
        }
    }.toTypedArray()
}