package com.example.runforrun.domain.tracking.location

import com.example.runforrun.domain.model.LocationTracking

interface LocationTrackingService {
    fun setCallback(locationCallback: LocationCallback)
    fun removeCallback()
    interface LocationCallback {
        fun locationUpdate(results: List<LocationTracking>)
    }
}