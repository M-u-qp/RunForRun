package com.example.runforrun.ui.screens.statistics.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.ui.screens.statistics.RunningStatisticsState
import io.github.koalaplot.core.ChartLayout
import io.github.koalaplot.core.Symbol
import io.github.koalaplot.core.legend.FlowLegend
import io.github.koalaplot.core.legend.LegendLocation
import io.github.koalaplot.core.line.LinePlot
import io.github.koalaplot.core.style.LineStyle
import io.github.koalaplot.core.util.ExperimentalKoalaPlotApi
import io.github.koalaplot.core.util.VerticalRotation
import io.github.koalaplot.core.util.generateHueColorPalette
import io.github.koalaplot.core.util.rotateVertically
import io.github.koalaplot.core.xygraph.CategoryAxisModel
import io.github.koalaplot.core.xygraph.DefaultPoint
import io.github.koalaplot.core.xygraph.FloatLinearAxisModel
import io.github.koalaplot.core.xygraph.XYGraph
import io.github.koalaplot.core.xygraph.XYGraphScope
import kotlin.math.ceil

private val colorMap = buildMap {
    val colors = generateHueColorPalette(RunningStatisticsState.Statistic.entries.size)
    RunningStatisticsState.Statistic.entries.forEachIndexed { index, statistic ->
        put(statistic, colors[index])
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
fun XYLinePlot(
    thumbnail: Boolean,
    selectedStatistic: RunningStatisticsState.Statistic,
    dailyData: List<Float>,
    maxData: Float,
    onStatisticSelected: (RunningStatisticsState.Statistic) -> Unit
) {
    val context = LocalContext.current

    ChartLayout(
        modifier = paddingMod,
        title = {},
        legend = {
            Legend(
                thumbnail = thumbnail,
                onStatisticSelected = onStatisticSelected,
                context = context
            )
        },
        legendLocation = LegendLocation.BOTTOM
    ) {
        XYGraph(
            xAxisModel = CategoryAxisModel(DaysOfWeek.getDaysOfWeek()),
            yAxisModel =
            if (!thumbnail) {
                when (selectedStatistic) {
                    RunningStatisticsState.Statistic.DISTANCE -> {
                        FloatLinearAxisModel(
                            0f..(ceil(maxData / 10.0) * 10.0).toFloat(),
                            minimumMajorTickSpacing = 50.dp
                        )
                    }

                    RunningStatisticsState.Statistic.DURATION -> {
                        FloatLinearAxisModel(
                            0f..(ceil(maxData / 5.0) * 5.0).toFloat(),
                            minimumMajorTickSpacing = 50.dp
                        )
                    }

                    RunningStatisticsState.Statistic.CALORIES -> {
                        FloatLinearAxisModel(
                            0f..(ceil(maxData / 500.0) * 500.0).toFloat(),
                            minimumMajorTickSpacing = 50.dp
                        )
                    }
                }
            } else {
                FloatLinearAxisModel(
                    0f..(ceil(10.0 / 50.0) * 50.0).toFloat(),
                    minimumMajorTickSpacing = 50.dp,
                )
            },
            xAxisLabels = {
                if (!thumbnail) {
                    AxisLabel(it, Modifier.padding(top = 2.dp))
                }
            },
            xAxisTitle = {
                if (!thumbnail) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        val week = stringResource(id = R.string.week)
                        AxisTitle(week)
                    }
                }
            },
            yAxisLabels = {
                if (!thumbnail) AxisLabel(it.toString(), Modifier.absolutePadding(right = 2.dp))
            },
            yAxisTitle = {
                if (!thumbnail) {
                    Box(
                        modifier = Modifier.fillMaxHeight(),
                        contentAlignment = Alignment.TopStart
                    ) {
                        val km = stringResource(id = R.string.km)
                        val min = stringResource(id = R.string.min)
                        val kcal = stringResource(id = R.string.kcal)

                        AxisTitle(
                            when (selectedStatistic) {
                                RunningStatisticsState.Statistic.DISTANCE -> "${
                                    RunningStatisticsState.Statistic.DISTANCE.getDisplayName(
                                        context
                                    )
                                }($km)"

                                RunningStatisticsState.Statistic.DURATION -> "${
                                    RunningStatisticsState.Statistic.DURATION.getDisplayName(
                                        context
                                    )
                                }($min)"

                                RunningStatisticsState.Statistic.CALORIES -> "${
                                    RunningStatisticsState.Statistic.CALORIES.getDisplayName(
                                        context
                                    )
                                }($kcal)"
                            },
                            modifier = Modifier
                                .rotateVertically(VerticalRotation.COUNTER_CLOCKWISE)
                                .padding(bottom = padding)
                        )
                    }
                }
            }
        ) {
            Chart(
                selectedStatistic = selectedStatistic,
                data = dailyData.mapIndexed { index, value ->
                    DefaultPoint(DaysOfWeek.getDaysOfWeek()[index], value)
                },
                thumbnail = thumbnail
            )
        }
    }
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun XYGraphScope<String, Float>.Chart(
    selectedStatistic: RunningStatisticsState.Statistic,
    data: List<DefaultPoint<String, Float>>,
    thumbnail: Boolean
) {
    val color = colorMap[selectedStatistic] ?: Color.Black
    var isVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LinePlot(
        data = data,
        lineStyle = LineStyle(
            brush = SolidColor(color),
            strokeWidth = 2.dp
        ),
        symbol = { point ->
            Symbol(
                shape = CircleShape,
                fillBrush = SolidColor(color),
                modifier = Modifier
                    .clickable {
                        isVisible = !isVisible
                        if (!thumbnail && isVisible) {
                            Toast.makeText(context, "${point.y}", Toast.LENGTH_SHORT).show()
                        }
                    }
            )
        }
    )
}

@OptIn(ExperimentalKoalaPlotApi::class)
@Composable
private fun Legend(
    thumbnail: Boolean = false,
    onStatisticSelected: (RunningStatisticsState.Statistic) -> Unit,
    context: Context
) {
    if (!thumbnail) {
        val statistics = RunningStatisticsState.Statistic.entries

        FlowLegend(
            itemCount = statistics.size,
            symbol = { i ->
                Symbol(
                    modifier = Modifier.size(padding),
                    fillBrush = SolidColor(colorMap[statistics[i]] ?: Color.Black)
                )
            },
            label = { i ->
                OutlinedButton(onClick = { onStatisticSelected(statistics[i]) }) {
                    Text(
                        text = statistics[i].getDisplayName(context),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            modifier = paddingMod
        )
    }
}