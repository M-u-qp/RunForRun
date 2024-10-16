package com.example.runforrun.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val USER_LANGUAGE = stringPreferencesKey("user_language")
    }

    val userLanguage = dataStore.data.map { preferences ->
        preferences[USER_LANGUAGE] ?: "ru"
    }

    suspend fun updateLanguage(languageCode: String) = dataStore.edit { preferences ->
        preferences[USER_LANGUAGE] = languageCode
    }
}