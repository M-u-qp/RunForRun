package com.example.runforrun.ui.screens.profile

import android.content.Context
import android.net.Uri

interface ProfileEvent {
    fun beginEdit()
    fun saveProfile(context: Context)
    fun changeUserName(newName: String)
    fun changeUserImage(newUri: Uri?)
    fun discardChanges()
}