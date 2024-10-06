package com.example.runforrun.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.ui.screens.home.components.EmptyRunList
import com.example.runforrun.ui.screens.home.components.HomeTopBar
import com.example.runforrun.ui.screens.home.components.RecentRunListActivity

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToRun: () -> Unit
) {
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()
    val duration by viewModel.duration.collectAsStateWithLifecycle()

    if (doesUserExist == true) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeTopBar(
                modifier = Modifier.zIndex(1f),
                user = state.user,
                weeklyGoal = state.user.weeklyGoal,
                distanceCovered = 0.0f,
                onWeeklyGoalClick = navigateToRun,
                duration = duration,
                navigateToRun = navigateToRun
            )

            Row(
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(30.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(id = R.string.recent_activity),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                TextButton(
                    onClick = {},
                    modifier = Modifier,
                ) {
                    Text(
                        text = stringResource(id = R.string.all),
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(state = rememberScrollState())
                    .padding(bottom = compositionLocalOf { 0.dp }.current)
            ) {
                if (state.runList.isEmpty()) {
                    EmptyRunList()
                } else {
                    RecentRunListActivity(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        runList = state.runList,
                        onClick = {}
                    )
                }
            }

//            ElevatedButton(
//                modifier = Modifier
//                    .align(Alignment.CenterHorizontally)
//                    .zIndex(1f)
//                    .size(70.dp),
//                shape = CircleShape,
//                colors = ButtonDefaults.elevatedButtonColors(
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                ),
//                elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
//                onClick = navigateToRun
//            ) {
//                Icon(
//                    modifier = Modifier.scale(2f),
//                    bitmap = ImageBitmap.imageResource(id = R.drawable.run_next),
//                    contentDescription = null
//                )
//            }
        }
    }
}