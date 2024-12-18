package com.example.runforrun.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.common.utils.FileUts.shareRun
import com.example.runforrun.ui.components.RunningDialog
import com.example.runforrun.ui.screens.home.components.EmptyRunList
import com.example.runforrun.ui.screens.home.components.HomeTopBar
import com.example.runforrun.ui.screens.home.components.RecentRunListActivity
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToRun: () -> Unit,
    navigateToAllRuns: () -> Unit,
    navigateToSettings: () -> Unit,
    paddingValues: PaddingValues
) {
    val state by viewModel.homeScreenState.collectAsStateWithLifecycle()
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()
    val duration by viewModel.duration.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    if (doesUserExist == true) {
        Column {
            Box(modifier = Modifier.height(IntrinsicSize.Min)) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                        )
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp),
                            ambientColor = MaterialTheme.colorScheme.primary,
                            spotColor = MaterialTheme.colorScheme.primary
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                ) {
                    HomeTopBar(
                        modifier = Modifier
                            .zIndex(1f)
                            .padding(bottom = 12.dp),
                        user = state.user,
                        weeklyGoal = state.user.weeklyGoal,
                        distanceCovered = 0.0f,
                        onWeeklyGoalClick = navigateToRun,
                        duration = duration,
                        navigateToRun = navigateToRun,
                        navigateToSettings = navigateToSettings
                    )
                    Spacer(modifier = Modifier.size(24.dp))
                    Row(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = stringResource(id = R.string.recent_activity),
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        )
                        TextButton(
                            onClick = navigateToAllRuns,
                            modifier = Modifier,
                        ) {
                            Text(
                                text = stringResource(id = R.string.all),
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                )
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(state = rememberScrollState())
                    .padding(
                        top = 12.dp,
                        bottom = compositionLocalOf { 0.dp }.current
                    )
            ) {
                if (state.runList.isEmpty()) {
                    EmptyRunList()
                } else {
                    RecentRunListActivity(
                        modifier = Modifier.padding(horizontal = 24.dp),
                        runList = state.runList,
                        showRunDialog = viewModel::showRunDialog,
                        paddingValues = paddingValues
                    )
                }
            }
        }
        state.currentRunDetails?.let { run ->
            RunningDialog(
                run = run,
                dismiss = viewModel::dismissDialog,
                delete = { viewModel.deleteRun(run) },
                share = {
                    scope.launch {
                        shareRun(run, context)
                    }
                }
            )
        }
    }
}