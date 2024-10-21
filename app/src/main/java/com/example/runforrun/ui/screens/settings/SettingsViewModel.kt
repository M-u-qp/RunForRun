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
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel(), SettingsEvent {
    private val _selectedLanguage = MutableStateFlow<String?>(Locale.getDefault().language)
    val selectedLanguage = _selectedLanguage.asStateFlow()

    init {
        viewModelScope.launch {
            _selectedLanguage.value =
                settingsRepository.getSelectedLanguage()
        }
    }

    override suspend fun updateLanguage(context: Context, languageCode: String) {
        if (languageCode != selectedLanguage.value) {
            settingsRepository.changeApplicationLanguage(languageCode)
            _selectedLanguage.value = languageCode
            val configuration = context.resources.configuration
            configuration.setLocale(Locale(languageCode))
            context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
}