package com.example.runforrun.data.tracking.location

import android.app.Application
import android.os.Looper
import com.example.runforrun.common.extension.locationPermission
import com.example.runforrun.domain.model.Location
import com.example.runforrun.domain.model.LocationTracking
import com.example.runforrun.domain.tracking.location.LocationMonitoring
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

class LocationMonitoringImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Application,
    private val locationRequest: LocationRequest
) : LocationMonitoring {
    private var locationCallback: LocationMonitoring.LocationCallback? = null
    private val googleCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            locationCallback?.locationUpdate(
                p0.locations.mapNotNull {
                    it?.let {
                        LocationTracking(
                            location = Location(
                                latitude = it.latitude,
                                longitude = it.longitude
                            ),
                            speed = it.speed
                        )
                    }
                }
            )
        }
    }

    override fun setCallback(locationCallback: LocationMonitoring.LocationCallback) {
        if (context.locationPermission()) {
            try {
                this.locationCallback = locationCallback
                fusedLocationProviderClient.requestLocationUpdates(
                    locationRequest,
                    googleCallback,
                    Looper.getMainLooper()
                )
            } catch (_: SecurityException) {
            }
        }
    }

    override fun removeCallback() {
        this.locationCallback = null
        fusedLocationProviderClient.removeLocationUpdates(googleCallback)
    }

}