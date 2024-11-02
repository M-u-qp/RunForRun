package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.runtime.Composable

interface SampleView {
    val name: String
    val thumbnail: @Composable () -> Unit
    val content: @Composable () -> Unit
}