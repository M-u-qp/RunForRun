package com.example.runforrun.data.tracking

import android.annotation.SuppressLint
import android.app.Application
import android.os.Looper
import com.example.runforrun.domain.model.LocationCoordinates
import com.example.runforrun.domain.model.LocationTracking
import com.example.runforrun.domain.tracking.LocationTrackingManager
import com.example.runforrun.extensions.hasLocationPermission
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

@SuppressLint("MissingPermission")
class LocationTrackingManagerImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val application: Application,
    private val locationRequest: LocationRequest
) : LocationTrackingManager {
    private var locationCallback: LocationTrackingManager.LocationCallback? = null
    private val gLocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            locationCallback?.locationUpdate(
                p0.locations.mapNotNull {
                    it?.let {
                        LocationTracking(
                            locationCoordinates = LocationCoordinates(it.latitude, it.longitude),
                            speed = it.speed
                        )
                    }
                }
            )
        }
    }

    override fun setCallback(locationCallback: LocationTrackingManager.LocationCallback) {
        if (application .hasLocationPermission()) {
            this.locationCallback = locationCallback
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                gLocationCallback,
                Looper.getMainLooper()
            )
        }
    }


    override fun removeCallback() {
        this.locationCallback = null
        fusedLocationProviderClient.removeLocationUpdates(gLocationCallback)
    }
}