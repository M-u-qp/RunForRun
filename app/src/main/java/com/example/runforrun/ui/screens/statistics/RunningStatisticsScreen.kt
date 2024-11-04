package com.example.runforrun.ui.screens.statistics

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
        ) {
            Text(
                modifier = Modifier.padding(bottom = 6.dp),
                text = stringResource(id = R.string.weekly_report),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.size(20.dp))
            if (state.totalDistance != 0L) {
                RunningGraph(
                    onNextWeek = { viewModel.switchToNextWeek() },
                    onPreviousWeek = { viewModel.switchToPreviousWeek(context) },
                    currentWeekLabel = viewModel.getCurrentWeekLabel(),
                    onStatisticSelected = { selectStatistic ->
                        viewModel.selectStatistic(
                            selectStatistic
                        )
                    },
                    state = state,
                    thumbnail = false
                )
            } else {
                RunningGraph(
                    onNextWeek = {},
                    onPreviousWeek = {},
                    currentWeekLabel = viewModel.getCurrentWeekLabel(),
                    onStatisticSelected = {},
                    state = state,
                    thumbnail = true
                )
            }
        }
    }
}

@Composable
private fun RunningGraph(
    modifier: Modifier = Modifier,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    currentWeekLabel: String,
    onStatisticSelected: (RunningStatisticsState.Statistic) -> Unit,
    state: RunningStatisticsState,
    thumbnail: Boolean
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
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
                .weight(1f)
                .padding(padding),
            tonalElevation = 2.dp,
            shape = MaterialTheme.shapes.medium,
            border = BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            when (state.selectedStatistic) {
                RunningStatisticsState.Statistic.DISTANCE -> {
                    XYLinePlot(
                        thumbnail = thumbnail,
                        selectedStatistic = if (!thumbnail) state.selectedStatistic else RunningStatisticsState.Statistic.DISTANCE,
                        dailyData = if (!thumbnail) state.dailyDistances else listOf(),
                        maxData = if (!thumbnail) state.totalDistance else 0L,
                        onStatisticSelected = onStatisticSelected
                    )
                }

                RunningStatisticsState.Statistic.DURATION -> {
                    XYLinePlot(
                        thumbnail = thumbnail,
                        selectedStatistic = if (!thumbnail) state.selectedStatistic else RunningStatisticsState.Statistic.DURATION,
                        dailyData = if (!thumbnail) state.dailyDurations else listOf(),
                        maxData = if (!thumbnail) state.totalDuration else 0L,
                        onStatisticSelected = onStatisticSelected
                    )
                }

                RunningStatisticsState.Statistic.CALORIES -> {
                    XYLinePlot(
                        thumbnail = thumbnail,
                        selectedStatistic = if (!thumbnail) state.selectedStatistic else RunningStatisticsState.Statistic.CALORIES,
                        dailyData = if (!thumbnail) state.dailyCalories else listOf(),
                        maxData = if (!thumbnail) state.totalCaloriesBurned else 0L,
                        onStatisticSelected = onStatisticSelected
                    )
                }
            }
        }
    }
}