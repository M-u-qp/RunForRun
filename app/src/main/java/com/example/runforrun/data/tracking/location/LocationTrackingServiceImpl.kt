package com.example.runforrun.data.tracking.location

import android.content.Context
import com.example.runforrun.domain.tracking.location.LocationTrackingService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest

class LocationTrackingServiceImpl(
    private val fusedLocationProviderClient: FusedLocationProviderClient,
    private val context: Context,
    private val locationRequest: LocationRequest
) : LocationTrackingService {
    override fun setCallback(locationCallback: LocationTrackingService.LocationCallback) {
        TODO("Not yet implemented")
    }

    override fun removeCallback() {
        TODO("Not yet implemented")
    }

}