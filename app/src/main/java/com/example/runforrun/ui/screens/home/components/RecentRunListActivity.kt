package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.runforrun.data.model.Run

@Composable
fun RecentRunListActivity(
    modifier: Modifier = Modifier,
    runList: List<Run>,
    onClick: (Run) -> Unit
) {
    runList.forEachIndexed { _, run ->
        Column(
            modifier = modifier
        ) {
            RunItem(
                run = run,
                modifier = Modifier
                    .clickable { onClick(run) }
            )
            Spacer(modifier = Modifier.size(8.dp))
        }
    }
}