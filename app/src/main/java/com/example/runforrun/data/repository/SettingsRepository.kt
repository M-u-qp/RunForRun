package com.example.runforrun.data.repository

import android.content.SharedPreferences
import java.util.Locale
import javax.inject.Inject

class SettingsRepository @Inject constructor(
//    private val dataStore: DataStore<Preferences>,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val SELECTED_LANGUAGE = "selected_language"
        const val ENGLISH = "en"
        const val RUSSIAN = "ru"
    }

    fun changeApplicationLanguage(languageCode: String) {
        with(sharedPreferences.edit()) {
            when(languageCode) {
                RUSSIAN -> {
                    putString(SELECTED_LANGUAGE, languageCode)
                    apply()
                }
                ENGLISH -> {
                    putString(SELECTED_LANGUAGE, languageCode)
                    apply()
                }
            }
        }
    }

    fun getSelectedLanguage(): String? {
        return sharedPreferences.getString(SELECTED_LANGUAGE, Locale.getDefault().language)
    }

//    suspend fun changeApplicationLanguage(language: String) {
//        dataStore.edit { preferences ->
//            when (language) {
//                ENGLISH -> preferences[SELECTED_LANGUAGE] = ENGLISH
//                RUSSIAN -> preferences[SELECTED_LANGUAGE] = RUSSIAN
//            }
//        }
//    }
//
//    suspend fun getSelectedLanguage(): String? {
//        val preferences = dataStore.data.first()
//        return preferences[SELECTED_LANGUAGE]
//    }
}