package com.example.runforrun.ui.screens.settings

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.SettingsRepository
import com.example.runforrun.ui.MainActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel(), SettingsEvent {
    private val _selectedLanguage = MutableStateFlow<String?>(Locale.getDefault().language)
    val selectedLanguage = _selectedLanguage.asStateFlow()

    private val _achieveVisible = MutableStateFlow(false)
    val achieveVisible = _achieveVisible.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedLanguage.value =
                settingsRepository.getSelectedLanguage()

            _achieveVisible.value = settingsRepository.isAchievementsVisible().first()
        }
    }

    override suspend fun updateLanguage(context: Context, languageCode: String) {
        if (languageCode != selectedLanguage.value) {
            settingsRepository.changeApplicationLanguage(languageCode)
            _selectedLanguage.value = languageCode

            var fContext = context
            val configuration = fContext.resources.configuration
            val systemLocale = configuration.locales.get(0)
            if (languageCode != "" && systemLocale.language != languageCode) {
                val locale = Locale(languageCode)
                Locale.setDefault(locale)
                configuration.setLocale(locale)
                fContext = fContext.createConfigurationContext(configuration)
            }

            val intent = Intent(fContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            fContext.startActivity(intent)
        }
    }

    fun setAchievementsVisibility(isVisible: Boolean) {
        viewModelScope.launch {
            settingsRepository.setAchievementsVisibility(isVisible)
            _achieveVisible.value = isVisible
        }
    }
}