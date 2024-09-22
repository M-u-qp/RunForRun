package com.example.runforrun.domain.tracking

import com.example.runforrun.domain.model.LocationTracking

interface LocationTrackingManager {
    fun setCallback(locationCallback: LocationCallback)
    fun removeCallback()
    interface LocationCallback {
        fun locationUpdate(results: List<LocationTracking>)
    }
}