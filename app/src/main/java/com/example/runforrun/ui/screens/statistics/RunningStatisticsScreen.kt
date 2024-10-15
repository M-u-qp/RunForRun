package com.example.runforrun.ui.screens.statistics

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.common.extension.toCalendar
import com.example.runforrun.common.extension.toList
import com.example.runforrun.ui.screens.statistics.components.DateRangeCard
import com.example.runforrun.ui.screens.statistics.components.FilteringStatistic
import com.example.runforrun.ui.screens.statistics.components.RunningStatisticsGraphCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunningStatisticsScreen(
    viewModel: RunningStatisticsViewModel = hiltViewModel(), navigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val dateList = remember(state.dateRange) {
        (state.dateRange.start.toCalendar()..state.dateRange.endInclusive.toCalendar()).toList()
    }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.statistics)) },
            navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(id = R.drawable.backward),
                        contentDescription = null
                    )
                }
            })
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
            DateRangeCard(dateList = dateList.map { it.time },
                incrementDate = viewModel::incrementRange,
                decrementDate = viewModel::decrementRange,
                isDateAvailable = { state.statisticsOnDate.containsKey(it) })
            FilteringStatistic(
                selected = state.statistic, selectChip = viewModel::selectStatistic
            )
            RunningStatisticsGraphCard(
                modifier = Modifier.fillMaxWidth(),
                runningStatistics = state.statisticsOnDate,
                dateRange = state.dateRange,
                statistics = state.statistic
            )
        }
    }
}