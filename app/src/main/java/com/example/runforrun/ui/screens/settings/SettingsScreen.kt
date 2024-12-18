package com.example.runforrun.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.ui.components.DefaultTopBar
import com.example.runforrun.ui.screens.settings.components.AchieveVisibleCard
import com.example.runforrun.ui.screens.settings.components.LanguageCard
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val selectedLanguage by viewModel.selectedLanguage.collectAsStateWithLifecycle()
    val achieveVisible by viewModel.achieveVisible.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            DefaultTopBar(
                navigateUp = navigateUp,
                title = stringResource(id = R.string.settings)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .verticalScroll(state = rememberScrollState())
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            selectedLanguage?.let { language ->
                LanguageCard(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    selectedLanguage = language,
                    onLanguageChange = { code ->
                        scope.launch {
                            viewModel.updateLanguage(context, code)
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            AchieveVisibleCard(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .fillMaxWidth(),
                isVisible = achieveVisible,
                onVisibleChange = { code ->
                    scope.launch {
                        viewModel.setAchievementsVisibility(code)
                    }
                }
            )
        }
    }
}