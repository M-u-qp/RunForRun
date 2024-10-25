package com.example.runforrun.ui.screens.achievements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.runforrun.R
import com.example.runforrun.ui.components.DefaultTopBar

@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    Scaffold(
        topBar = {
            DefaultTopBar(
                navigateUp = navigateUp,
                title = stringResource(id = R.string.achievements)
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            
        }
    }
}