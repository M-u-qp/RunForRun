package com.example.runforrun.common.extension

import com.example.runforrun.domain.model.Location
import com.google.android.gms.maps.model.LatLng

fun LatLng.toLocation() = Location(
    latitude = latitude,
    longitude = longitude
)