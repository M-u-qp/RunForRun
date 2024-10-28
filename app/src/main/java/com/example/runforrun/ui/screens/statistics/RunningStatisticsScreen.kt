package com.example.runforrun.ui.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.runforrun.R
import com.example.runforrun.ui.components.DefaultTopBar

@Composable
fun RunningStatisticsScreen(
    viewModel: RunningStatisticsViewModel = hiltViewModel(), navigateUp: () -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            DefaultTopBar(
                navigateUp = navigateUp,
                title = stringResource(id = R.string.statistics)
            )
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues = paddingValues)
                .padding(20.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = stringResource(id = R.string.weekly_report),
                style = MaterialTheme.typography.titleMedium
            )

        }
    }
}