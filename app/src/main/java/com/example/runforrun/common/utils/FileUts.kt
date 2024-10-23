package com.example.runforrun.common.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.example.runforrun.R
import com.example.runforrun.data.model.Run
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object FileUts {
    private const val SHARED_IMAGE = "shared_image.png"
    suspend fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
        deleteFile(context)
        val file = File(context.cacheDir, SHARED_IMAGE)
        return try {
            withContext(Dispatchers.IO) {
                FileOutputStream(file).use { out ->
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                }
            }
            FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun deleteFile(context: Context) {
        val file = File(context.cacheDir, SHARED_IMAGE)
        if (file.exists()) {
            file.delete()
        }
    }

    suspend fun shareRun(run: Run, context: Context) {
        val runningWas = context.getString(R.string.running_was)
        val tookTime = context.getString(R.string.took_time)
        val caloriesBurned = context.getString(R.string.calories_burned)
        val km = context.getString(R.string.km)
        val kcal = context.getString(R.string.kcal)
        val imageUri = saveBitmapToFile(context, run.image)
        val description = "${FormatterUts.getFormattedDate(run.timestamp)}\n" +
                "$runningWas: ${run.distance / 1000f} $km\n" +
                "$tookTime: ${FormatterUts.getFormattedTime(run.duration)}\n" +
                "$caloriesBurned: ${run.caloriesBurned} $kcal"
        if (imageUri != null) {
            Intent(Intent.ACTION_SEND).also {
                it.putExtra(Intent.EXTRA_STREAM, imageUri)
                it.putExtra(Intent.EXTRA_TEXT, description)
                it.type = "image/jpeg"
                it.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                if (it.resolveActivity(context.packageManager) != null) {
                    context.startActivity(it)
                }
            }
        }
    }
}