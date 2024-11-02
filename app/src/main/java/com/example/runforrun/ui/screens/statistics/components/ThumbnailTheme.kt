package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import io.github.koalaplot.core.style.KoalaPlotTheme

@Composable
fun ThumbnailTheme(content: @Composable () -> Unit) {
    KoalaPlotTheme(
        axis = KoalaPlotTheme.axis.copy(
            majorTickSize = 0.dp,
            minorTickSize = 0.dp,
            majorGridlineStyle = null,
            minorGridlineStyle = null
        ),
        content = content
    )
}