package com.example.runforrun.ui.screens.profile

import android.content.Context
import android.net.Uri

interface ProfileEvent {
    fun beginEdit()
    fun saveProfile()
    fun changeUserName(newName: String)
    fun changeUserImage(newUri: Uri?)
    fun discardChanges()
    fun saveImageToLocalStorage(context: Context, uri: Uri)
}