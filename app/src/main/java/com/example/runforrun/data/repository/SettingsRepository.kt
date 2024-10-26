package com.example.runforrun.data.repository

import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.Locale
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val SELECTED_LANGUAGE = "selected_language"
        const val ENGLISH = "en"
        const val RUSSIAN = "ru"

        val VISIBLE_ACHIEVEMENTS = booleanPreferencesKey("visible_achievements")
    }

    //Смена языка в настройках
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

    //Получение выбранного языка
    fun getSelectedLanguage(): String? {
        return sharedPreferences.getString(SELECTED_LANGUAGE, Locale.getDefault().language)
    }

    //Видимость достижений в профиле
    suspend fun setAchievementsVisibility(isVisible: Boolean) {
        dataStore.edit { preferences ->
            preferences[VISIBLE_ACHIEVEMENTS] = isVisible
        }
    }
    fun isAchievementsVisible(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[VISIBLE_ACHIEVEMENTS] ?: false
        }
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