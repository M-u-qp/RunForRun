package com.example.runforrun.data.model

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "run_table")
data class Run(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var distance: Int = 0,
    var duration: Long = 0L,
    var caloriesBurned: Int = 0,
    var avgSpeed: Float = 0.0f,
    var timestamp: Date = Date(),
    var image: Bitmap
)
