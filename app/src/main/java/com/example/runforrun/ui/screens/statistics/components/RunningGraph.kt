package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.utils.FormatterUts
import com.example.runforrun.ui.screens.statistics.RunningStatisticsState
import io.github.koalaplot.core.bar.BulletGraphs
import io.github.koalaplot.core.style.KoalaPlotTheme
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.xygraph.FloatLinearAxisModel
import java.math.RoundingMode

@Composable
fun RunningGraph(
    modifier: Modifier = Modifier,
    distance: Long,
    duration: Long,
    caloriesBurned: Long,
    onPreviousWeek: () -> Unit,
    onNextWeek: () -> Unit,
    currentWeekLabel: String
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

        Graph(
            label = RunningStatisticsState.Statistic.DISTANCE.toString(),
            value = distance / 1000f,
            range = 0f..10f
        )
        Graph(
            label = RunningStatisticsState.Statistic.DURATION.toString(),
            value = duration.toBigDecimal()
                .divide((3_600_000).toBigDecimal(), 2, RoundingMode.HALF_UP)
                .toFloat(),
            range = 0f..12f,
            durationValue = duration
        )
        Graph(
            label = RunningStatisticsState.Statistic.CALORIES.toString(),
            value = caloriesBurned.toFloat(),
            range = 0f..1000f
        )
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun Graph(
    label: String,
    value: Float,
    range: ClosedFloatingPointRange<Float>,
    durationValue: Long = 0L
) {
    var formattedValue = ""
    var valueText = ""
    when (label) {
        RunningStatisticsState.Statistic.DISTANCE.toString() -> {
            valueText = stringResource(id = R.string.km)
            formattedValue = value.toString()
        }

        RunningStatisticsState.Statistic.DURATION.toString() -> {
            valueText = stringResource(id = R.string.running_time)
            formattedValue = FormatterUts.getFormattedTime(durationValue)
        }

        RunningStatisticsState.Statistic.CALORIES.toString() -> {
            valueText = stringResource(id = R.string.kcal)
            formattedValue = value.toString()
        }
    }

    BulletGraphs(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {
        bullet(FloatLinearAxisModel(range)) {
            label {
                Column(
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier.padding(end = KoalaPlotTheme.sizes.gap)
                ) {
                    Text(
                        text = label,
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "($valueText: $formattedValue)",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            axis {
                labels {
                    Text(
                        text = "${it.toInt()}",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            comparativeMeasure(value)
            featuredMeasureBar(value)
            ranges(
                range.start,
                range.endInclusive / 3,
                range.endInclusive * 2 / 3,
                range.endInclusive
            )
        }
    }
}