package com.example.runforrun.ui.screens.settings

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.runforrun.data.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel(), SettingsEvent {
    private val _selectedLanguage = MutableStateFlow("")
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        settingsRepository.userLanguage
            .onEach { language -> _selectedLanguage.update { language } }
            .launchIn(viewModelScope)
    }

    override suspend fun updateLanguage(context: Context, languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = context.resources.configuration.apply {
            setLocale(locale)
        }
        context.createConfigurationContext(config)
        _selectedLanguage.update { languageCode }
        settingsRepository.updateLanguage(languageCode)
        (context as? Activity)?.recreate()
    }
}