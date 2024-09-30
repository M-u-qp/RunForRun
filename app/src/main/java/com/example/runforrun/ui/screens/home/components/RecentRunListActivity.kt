package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.runforrun.data.model.Run

@Composable
fun RecentRunListActivity(
    modifier: Modifier = Modifier,
    runList: List<Run>,
    onClick: (Run) -> Unit
) {
    ElevatedCard(
        modifier = modifier
            .padding(bottom = 8.dp)
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column {
            runList.forEachIndexed { i, run ->
                Column {
                    RunItem(
                        run = run,
                        modifier = Modifier
                            .clickable { onClick(run) }
                            .padding(16.dp)
                    )
                    if (i < runList.lastIndex) {
                        Box(
                            modifier = Modifier
                                .height(1.dp)
                                .width(200.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(
                                        alpha = 0.2f
                                    )
                                )
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
            }
        }
    }
}