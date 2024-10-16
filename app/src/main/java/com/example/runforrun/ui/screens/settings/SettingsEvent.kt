package com.example.runforrun.ui.screens.settings

import android.content.Context

interface SettingsEvent {
   suspend fun updateLanguage(context: Context, languageCode: String)
}