package com.example.runforrun.common.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.ui.geometry.Offset
import androidx.core.content.ContextCompat
import com.example.runforrun.common.extension.toLatLng
import com.example.runforrun.domain.model.PathPoint
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.coroutines.delay

object GoogleMapUts {
    private const val SNAPSHOT_DELAY = 500L

    suspend fun takeSnapshot(
        map: GoogleMap,
        pathPoints: List<PathPoint>,
        center: Offset,
        snapshot: (Bitmap) -> Unit,
        snapSideLength: Float
    ) {
        val builder = LatLngBounds.Builder()
        pathPoints.forEach {
            if (it is PathPoint.LocationPoint) {
                builder.include(it.location.toLatLng())
            }
        }
        map.moveCamera(
            CameraUpdateFactory
                .newLatLngBounds(
                    builder.build(),
                    snapSideLength.toInt(),
                    snapSideLength.toInt(),
                    (snapSideLength * 0.2).toInt()
                )
        )
        val startOffset = center - Offset(snapSideLength / 2, snapSideLength / 2)
        delay(SNAPSHOT_DELAY)
        map.snapshot {
            it?.let {
                val crop = Bitmap.createBitmap(
                    it,
                    startOffset.x.toInt(),
                    startOffset.y.toInt(),
                    snapSideLength.toInt(),
                    snapSideLength.toInt()
                )
                snapshot(crop)
            }
        }
    }

    fun bitmapDescriptorFromVector(
        context: Context,
        @DrawableRes vectorResId: Int,
        tint: Int? = null,
        sizeInPx: Int? = null
    ): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)!!
        tint?.let { vectorDrawable.setTint(it) }
        vectorDrawable.setBounds(
            0,
            0,
            sizeInPx ?: vectorDrawable.intrinsicWidth,
            sizeInPx ?: vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            sizeInPx ?: vectorDrawable.intrinsicWidth,
            sizeInPx ?: vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}