package com.example.runforrun.ui.screens.run

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.common.animation.SlideAnimatedVisibility
import com.example.runforrun.common.utils.LocationUts
import com.example.runforrun.ui.screens.run.components.Map
import com.example.runforrun.ui.screens.run.components.RunTopBar
import com.example.runforrun.ui.screens.run.components.RunningStatsCard

@Composable
fun RunScreen(
    viewModel: RunViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        LocationUts.requestLocationSetting(context as Activity)
    }

    val state by viewModel.runStateAndCalories.collectAsStateWithLifecycle()
    val runningDuration by viewModel.runningDuration.collectAsStateWithLifecycle()
    var runFinished by rememberSaveable { mutableStateOf(false) }
    val showRunningCard by rememberSaveable { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Map(
            pathPoints = state.currentRunning.pathPoints,
            runFinished = runFinished
        ) {
            viewModel.finishRun(it)
            navigateUp()
        }
        RunTopBar(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp),
            navigateUp = navigateUp
        )
        SlideAnimatedVisibility.SlideUp(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = showRunningCard
        ) {
            RunningStatsCard(
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 24.dp),
                state = state,
                playOrPauseClick = viewModel::playOrPauseRun,
                duration = runningDuration,
                finish = { runFinished = true }
            )
        }
    }
}