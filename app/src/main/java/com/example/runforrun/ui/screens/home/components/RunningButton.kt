package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.runforrun.R

@Composable
fun RunningButton(
    modifier: Modifier = Modifier,
    navigateToRun: () -> Unit
) {
    Box(
        modifier = modifier
            .size(70.dp)
            .zIndex(1f)
            .offset(y = 35.dp)
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .shadow(
                shape = CircleShape,
                elevation = 8.dp
            )
            .clickable { navigateToRun() }
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(5.dp)
                .background(
                    color = MaterialTheme.colorScheme.onPrimary,
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .size(40.dp),
                    bitmap = ImageBitmap.imageResource(id = R.drawable.run_next),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}