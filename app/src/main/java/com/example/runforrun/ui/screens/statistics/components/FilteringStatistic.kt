package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ElevatedFilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.ui.screens.statistics.RunningStatisticsState

@Composable
fun FilteringStatistic(
    modifier: Modifier = Modifier,
    selected: RunningStatisticsState.Statistic,
    selectChip: (RunningStatisticsState.Statistic) -> Unit
) {
    Row(
        modifier = modifier
            .padding(bottom = 20.dp)
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        RunningStatisticsState.Statistic.entries.forEach {
            ElevatedFilterChip(
                selected = it == selected,
                onClick = { selectChip(it) },
                label = {
                    Text(
                        text = it.toString(),
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = {
                    AnimatedVisibility(visible = it == selected) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(id = R.drawable.done),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    }
}