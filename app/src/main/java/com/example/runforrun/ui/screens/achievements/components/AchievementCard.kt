package com.example.runforrun.ui.screens.achievements.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp

@Composable
fun AchievementCard(
    imageBitmap: ImageBitmap,
    goalText: String,
    currentCompletion: String
) {
    var showTooltip by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.large
            )
            .clip(MaterialTheme.shapes.large)
            .clickable { showTooltip = !showTooltip }
            .verticalScroll(rememberScrollState())
    ) {
        if (showTooltip) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .alpha(0.7f),
                text = "$goalText\n$currentCompletion",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                )
            )
        } else {
            Image(
                bitmap = imageBitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(60.dp)
                    .align(Alignment.Center)
            )
        }
    }
}