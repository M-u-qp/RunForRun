package com.example.runforrun.common.extension

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.runforrun.common.utils.PermissionUts
import java.util.Locale

fun Context.notificationPermission() =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            this,
            PermissionUts.notificationPermission
        ) == PackageManager.PERMISSION_GRANTED
    } else true

fun Context.locationPermission() =
    PermissionUts.locationPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }


fun Context.mediaPermission() =
    PermissionUts.mediaPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

@RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
fun Context.allPermissions() =
    PermissionUts.allPermissions.all {
        ContextCompat.checkSelfPermission(
            this,
            it
        ) == PackageManager.PERMISSION_GRANTED
    }

fun Context.appSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}

fun Context.setAppLocale(language: String){
    val locale = Locale(language)
    Locale.setDefault(locale)
    val config = resources.configuration
    config.setLocale(locale)
    config.setLayoutDirection(locale)
    createConfigurationContext(config)
    resources.updateConfiguration(config, resources.displayMetrics)
}