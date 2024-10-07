package com.example.runforrun.ui.screens.all_runs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.runforrun.R
import com.example.runforrun.ui.screens.all_runs.components.AllRunsTopBar
import com.example.runforrun.ui.screens.all_runs.components.RunningDialog
import com.example.runforrun.ui.screens.all_runs.components.RunsList

@Composable
fun AllRunsScreen(
    viewModel: AllRunsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val runs = viewModel.runList.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            AllRunsTopBar(
                sortOrderSelected = viewModel::setSortOrder,
                navigateUp = navigateUp
            )
        }
    ) {
        Column(modifier = Modifier.padding(it)) {
            Text(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.your_activity),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.scrim
                )
            )
            RunsList(
                runs = runs,
                onClick = viewModel::setDialog
            )
        }
    }
    viewModel.dialog.collectAsStateWithLifecycle().value?.let {
        RunningDialog(
            run = it,
            dismiss = { viewModel.setDialog(null) },
            delete = { viewModel.deleteRun() }
        )
    }
}
