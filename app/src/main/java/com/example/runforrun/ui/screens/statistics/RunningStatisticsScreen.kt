package com.example.runforrun.ui.screens.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.runforrun.R
import com.example.runforrun.ui.components.DefaultTopBar
import com.example.runforrun.ui.screens.statistics.components.XYLinePlot
import com.example.runforrun.ui.screens.statistics.components.padding

@Composable
fun RunningStatisticsScreen(
    viewModel: RunningStatisticsViewModel = hiltViewModel(), navigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            DefaultTopBar(
                navigateUp = navigateUp,
                title = stringResource(id = R.string.statistics)
            )
        }) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
                .padding(20.dp)
                .verticalScroll(state = rememberScrollState())
        ) {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = stringResource(id = R.string.weekly_report),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(20.dp))
            if (state.totalDistance != 0f)
                RunningGraph(
                    onNextWeek = { viewModel.switchToNextWeek() },
                    onPreviousWeek = { viewModel.switchToPreviousWeek(context) },
                    currentWeekLabel = viewModel.getCurrentWeekLabel(),
                    selectedStatistic = state.selectedStatistic,
                    onStatisticSelected = { selectStatistic ->
                        viewModel.selectStatistic(
                            selectStatistic
                        )
                    },
                    state = state
                )
        }
    }
}

@Composable
private fun RunningGraph(
    modifier: Modifier = Modifier,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    currentWeekLabel: String,
    selectedStatistic: RunningStatisticsState.Statistic,
    onStatisticSelected: (RunningStatisticsState.Statistic) -> Unit,
    state: RunningStatisticsState
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(onClick = { onPreviousWeek() }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.backwardv2),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Text(
                text = currentWeekLabel,
                style = MaterialTheme.typography.titleMedium
            )
            IconButton(onClick = { onNextWeek() }) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.forwardv2),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Surface(
            modifier = Modifier
                .height(600.dp)
                .padding(padding),
            shadowElevation = 2.dp
        ) {
            when (selectedStatistic) {
                RunningStatisticsState.Statistic.DISTANCE -> {
                    XYLinePlot(
                        thumbnail = false,
                        selectedStatistic = selectedStatistic,
                        dailyData = state.dailyDistances,
                        maxData = state.totalDistance,
                        onStatisticSelected = onStatisticSelected
                    )
                }

                RunningStatisticsState.Statistic.DURATION -> {
                    XYLinePlot(
                        thumbnail = false,
                        selectedStatistic = selectedStatistic,
                        dailyData = state.dailyDurations,
                        maxData = state.totalDuration,
                        onStatisticSelected = onStatisticSelected
                    )
                }

                RunningStatisticsState.Statistic.CALORIES -> {
                    XYLinePlot(
                        thumbnail = false,
                        selectedStatistic = selectedStatistic,
                        dailyData = state.dailyCalories,
                        maxData = state.totalCaloriesBurned,
                        onStatisticSelected = onStatisticSelected
                    )
                }
            }
        }
    }
}