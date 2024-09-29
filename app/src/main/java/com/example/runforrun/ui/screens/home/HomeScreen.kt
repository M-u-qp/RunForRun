package com.example.runforrun.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.ui.screens.home.components.HomeTopBar

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()

    if (doesUserExist == true) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeTopBar(
                user = state.user,
                weeklyGoal = state.user.weeklyGoal,
                distanceCovered = 0.0f,
                onWeeklyGoalClick = {/*TODO*/ }
            )
        }
    }
}