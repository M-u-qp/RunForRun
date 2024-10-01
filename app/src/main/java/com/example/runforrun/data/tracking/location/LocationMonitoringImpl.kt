package com.example.runforrun.data.tracking.location

import android.content.Context
import com.example.runforrun.domain.tracking.location.LocationMonitoring
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest

class LocationMonitoringImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Context,
    private val locationRequest: LocationRequest
) : LocationMonitoring {
    override fun setCallback(locationCallback: LocationMonitoring.LocationCallback) {
        TODO("Not yet implemented")
    }

    override fun removeCallback() {
        TODO("Not yet implemented")
    }

}