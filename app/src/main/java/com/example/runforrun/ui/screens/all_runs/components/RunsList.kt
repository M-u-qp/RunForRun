package com.example.runforrun.ui.screens.all_runs.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.runforrun.data.model.Run
import com.example.runforrun.ui.components.RunningCard

@Composable
fun RunsList(
    runs: LazyPagingItems<Run>,
    onClick: (Run) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(bottom = compositionLocalOf { 0.dp }.current + 6.dp)
    ) {
        if (runs.loadState.refresh == LoadState.Loading) {
            item {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        } else {
            items(runs.itemCount) {
                runs[it]?.let { run ->
                    ElevatedCard(
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .clickable { onClick(run) }
                            .padding(horizontal = 16.dp)
                    ) {
                        RunningCard(
                            run = run,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp)
                        )
                    }
                }
            }
        }
    }
}