package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.extension.toCalendar
import com.example.runforrun.common.extension.toList
import com.example.runforrun.common.utils.ConverterUts
import com.example.runforrun.ui.screens.statistics.RunningStatisticsState
import com.example.runforrun.ui.screens.statistics.TotalStatisticsOnDate
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.component.shape.shader.color
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.shader.DynamicShaders
import com.patrykandpatrick.vico.core.component.text.TextComponent
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.lineSeries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun RunningStatisticsGraphCard(
    modifier: Modifier = Modifier,
    runningStatistics: Map<Date, TotalStatisticsOnDate>,
    dateRange: ClosedRange<Date>,
    statistics: RunningStatisticsState.Statistic
) {
    val dateList =
        remember(dateRange) { (dateRange.start.toCalendar()..dateRange.endInclusive.toCalendar()).toList() }
    val graphColor = MaterialTheme.colorScheme.primary
    val density = LocalDensity.current
    val indicatorSize = density.run { 3.dp.toPx() }
    val extraStoreKey = remember { ExtraStore.Key<List<Date>>() }
    val modelProducer = remember { CartesianChartModelProducer.build() }
    val marker = remember(graphColor, indicatorSize) {
        MarkerComponent(
            indicator = ShapeComponent(
                shape = Shapes.pillShape,
                color = graphColor.toArgb()
            ),
            label = TextComponent.build { textSizeSp = 0f }
        ).apply { indicatorSizeDp = indicatorSize }
    }
    val markers = remember(runningStatistics, marker) {
        buildMap {
            dateList.forEachIndexed { index, calendar ->
                if (runningStatistics.contains(calendar.time)) {
                    this[index.toFloat()] = marker
                }
            }
        }
    }

    Box(
        modifier = modifier
            .padding(horizontal = 10.dp)
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 30.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            TopStatistics(
                dateRange = dateRange,
                runningStatistics = runningStatistics.values,
                statistics = statistics
            )
            modelProducer.ProduceRunningStateModel(
                runningStatistics = runningStatistics,
                statistics = statistics,
                dateList = dateList,
                extraStoreKey = extraStoreKey
            )

            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(
                        lines = listOf(
                            rememberLineSpec(
                                shader = DynamicShaders.color(graphColor),
                                pointConnector = DefaultPointConnector(cubicStrength = 0.5f)
                            )
                        )
                    ),
                    startAxis = rememberStartAxis(),
                    bottomAxis = rememberBottomAxis(
                        valueFormatter = rememberBottomAxisValueFormatter(extraStoreKey = extraStoreKey),
                        itemPlacer = remember {
                            AxisItemPlacer.Horizontal.default(
                                addExtremeLabelPadding = true
                            )
                        },
                        guideline = null
                    ),
                    persistentMarkers = markers
                ),
                modelProducer = modelProducer,
                modifier = modifier,
                horizontalLayout = HorizontalLayout.fullWidth()
            )
        }
    }
}

@Composable
private fun rememberBottomAxisValueFormatter(
    extraStoreKey: ExtraStore.Key<List<Date>>,
    dateFormatter: SimpleDateFormat = remember { SimpleDateFormat("EEE", Locale.getDefault()) }
) = remember(extraStoreKey) {
        AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, chartValues, _ ->
            chartValues.model.extraStore[extraStoreKey][x.toInt()].let {
                dateFormatter.format(it)
            }
        }
    }

@Composable
private fun TopStatistics(
    dateRange: ClosedRange<Date>,
    runningStatistics: Collection<TotalStatisticsOnDate>,
    statistics: RunningStatisticsState.Statistic
) {
    val dateFormatter = remember { SimpleDateFormat("dd MMM", Locale.getDefault()) }
    val dateText = remember(dateRange) {
        dateFormatter.format(dateRange.start) + " - " + dateFormatter.format(dateRange.endInclusive)
    }
    val total by remember(runningStatistics, statistics) {
        derivedStateOf {
            when (statistics) {
                RunningStatisticsState.Statistic.DISTANCE -> {
                    val totalDistance = runningStatistics.sumOf { it.distance }
                    ConverterUts.converterMeterToKm(totalDistance.toLong()).toString()
                }

                RunningStatisticsState.Statistic.DURATION -> {
                    val totalDuration = runningStatistics.sumOf { it.duration }
                    ConverterUts.converterMillisToMinutes(totalDuration).toString()
                }

                RunningStatisticsState.Statistic.CALORIES -> {
                    runningStatistics.sumOf { it.caloriesBurned }.toString()
                }
            }
        }
    }

    Row(
        modifier = Modifier.height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.onSurface
                )
                .width(2.dp)
                .fillMaxHeight()
        )
        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(2.dp)) {
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = total,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    modifier = Modifier.alignByBaseline(),
                    text = when (statistics) {
                        RunningStatisticsState.Statistic.DISTANCE -> stringResource(
                            id = R.string.km
                        )

                        RunningStatisticsState.Statistic.DURATION -> stringResource(
                            id = R.string.min
                        )

                        RunningStatisticsState.Statistic.CALORIES -> stringResource(
                            id = R.string.kcal
                        )
                    },
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            Text(
                text = dateText,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun CartesianChartModelProducer.ProduceRunningStateModel(
    runningStatistics: Map<Date, TotalStatisticsOnDate>,
    statistics: RunningStatisticsState.Statistic,
    dateList: List<Calendar>,
    extraStoreKey: ExtraStore.Key<List<Date>>
) {
    LaunchedEffect(key1 = runningStatistics, extraStoreKey, statistics) {
        withContext(Dispatchers.Default) {
            tryRunTransaction {
                val c = dateList.map {
                    val currentStatistics = runningStatistics[it.time] ?: return@map 0
                    when (statistics) {
                        RunningStatisticsState.Statistic.DISTANCE -> {
                            currentStatistics.distance.toLong()
                        }

                        RunningStatisticsState.Statistic.DURATION -> {
                            ConverterUts.converterMillisToMinutes(currentStatistics.duration)
                        }

                        RunningStatisticsState.Statistic.CALORIES -> {
                            currentStatistics.caloriesBurned
                        }
                    }
                }
                lineSeries {
                    series(c)
                    updateExtras { it[extraStoreKey] = dateList.map { date -> date.time } }
                }
            }
        }
    }
}