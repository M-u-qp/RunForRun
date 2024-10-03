package com.example.runforrun.ui.screens.run.components

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import com.example.runforrun.R
import com.example.runforrun.common.extension.toLatLng
import com.example.runforrun.common.utils.GoogleMapUts
import com.example.runforrun.domain.common.extension.firstLocationPoint
import com.example.runforrun.domain.common.extension.lastLocationPoint
import com.example.runforrun.domain.model.Location
import com.example.runforrun.domain.model.PathPoint
import com.example.runforrun.ui.theme.green
import com.example.runforrun.ui.theme.light_primary
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun Map(
    modifier: Modifier = Modifier,
    pathPoints: List<PathPoint>,
    runFinished: Boolean,
    snapshot: (Bitmap) -> Unit
) {
    var size by remember { mutableStateOf(Size(0f, 0f)) }
    var center by remember { mutableStateOf(Offset(0f, 0f)) }
    var loaded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .onGloballyPositioned {
                val rect = it.boundsInRoot()
                size = rect.size
                center = rect.center
            }
    ) {
        ShowLoading(!loaded)
        val uiSettings = remember {
            MapUiSettings(
                mapToolbarEnabled = false,
                compassEnabled = true,
                zoomControlsEnabled = false
            )
        }
        val cameraState = rememberCameraPositionState {}
        val lastLocationPoint by remember(pathPoints) {
            derivedStateOf { pathPoints.lastLocationPoint() }
        }
        LaunchedEffect(key1 = lastLocationPoint) {
            lastLocationPoint?.let {
                cameraState.animate(
                    CameraUpdateFactory.newCameraPosition(
                        CameraPosition.fromLatLngZoom(it.location.toLatLng(), 15f)
                    )
                )
            }
        }
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            uiSettings = uiSettings,
            cameraPositionState = cameraState,
            onMapLoaded = { loaded = true }
        ) {
            DrawPathPoints(
                pathPoints = pathPoints,
                runFinished = runFinished
            )
            TakeScreenShot(
                take = runFinished,
                center = center,
                size = size,
                pathPoints = pathPoints,
                snapshot = snapshot
            )
        }
    }
}

@Composable
@GoogleMapComposable
private fun DrawPathPoints(
    pathPoints: List<PathPoint>,
    runFinished: Boolean
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val markerState = rememberMarkerState()
    val bigMarkerState = rememberMarkerState()
    val lastLocationPoint by remember(pathPoints) {
        derivedStateOf { pathPoints.lastLocationPoint() }
    }
    val firstLocationPoint by remember(pathPoints) {
        derivedStateOf { pathPoints.firstLocationPoint() }
    }
    val bigIcon = remember { with(density) { 32.dp.toPx().toInt() } }
    val littleIcon = remember { with(density) { 16.dp.toPx().toInt() } }
    val flagSize = remember { with(density) { 32.dp.toPx().toInt() } }
    val flagOffset = remember { Offset(0.5f, 0.8f) }

    LaunchedEffect(key1 = lastLocationPoint) {
        pathPoints.lastLocationPoint()?.let {
            val latLng = it.location.toLatLng()
            markerState.position = latLng
            bigMarkerState.position = latLng
        }
    }

    val locationList = mutableListOf<Location>()
    pathPoints.fastForEach { pathPoint ->
        if (pathPoint is PathPoint.EmptyLocationPoint) {
            Polyline(
                points = locationList.map { it.toLatLng() },
                color = MaterialTheme.colorScheme.primary
            )
            locationList.clear()
        } else if (pathPoint is PathPoint.LocationPoint) {
            locationList += pathPoint.location
        }
    }
    if (locationList.isNotEmpty()) {
        Polyline(
            points = locationList.map { it.toLatLng() },
            color = MaterialTheme.colorScheme.primary
        )
    }
    val positionIcon = remember(runFinished) {
        if (runFinished.not()) {
            GoogleMapUts.bitmapDescriptorFromVector(
                context = context,
                vectorResId = R.drawable.ic_circle,
                tint = light_primary.toArgb(),
                sizeInPx = littleIcon
            )
        } else {
            GoogleMapUts.bitmapDescriptorFromVector(
                context = context,
                vectorResId = R.drawable.ic_location_marker,
                tint = Color.RED,
                sizeInPx = flagSize
            )
        }
    }
    val positionBigIcon = remember(runFinished) {
        if (runFinished) {
            return@remember null
        }
        GoogleMapUts.bitmapDescriptorFromVector(
            context = context,
            vectorResId = R.drawable.ic_circle,
            tint = light_primary.copy(alpha = 0.4f).toArgb(),
            sizeInPx = bigIcon
        )
    }
    positionBigIcon?.let {
        Marker(
            icon = positionBigIcon,
            state = bigMarkerState,
            anchor = Offset(0.5f, 0.5f),
            visible = lastLocationPoint != null
        )
    }
    Marker(
        icon = positionIcon,
        state = markerState,
        anchor = if (runFinished) flagOffset else Offset(0.5f, 0.5f),
        visible = lastLocationPoint != null
    )
    firstLocationPoint?.let {
        val firstIcon = remember(runFinished) {
            GoogleMapUts.bitmapDescriptorFromVector(
                context = context,
                vectorResId = R.drawable.ic_location_marker,
                tint = green.toArgb(),
                sizeInPx = flagSize
            )
        }
        Marker(
            icon = firstIcon,
            state = rememberMarkerState(position = it.location.toLatLng()),
            anchor = flagOffset
        )
    }
}

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun TakeScreenShot(
    take: Boolean,
    center: Offset,
    size: Size,
    pathPoints: List<PathPoint>,
    snapshot: (Bitmap) -> Unit
) {
    MapEffect(key1 = take) { map ->
        if (take) {
            GoogleMapUts.takeSnapshot(
                map = map,
                pathPoints = pathPoints,
                center = center,
                snapshot = snapshot,
                snapSideLength = size.width / 2
            )
        }
    }
}

@Composable
private fun ShowLoading(visible: Boolean = false) {
    AnimatedVisibility(
        modifier = Modifier.fillMaxSize(),
        visible = visible,
        enter = EnterTransition.None,
        exit = fadeOut()
    ) {
        CircularProgressIndicator(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.background)
                .wrapContentSize()
        )
    }
}