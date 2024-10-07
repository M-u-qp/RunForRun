package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
    ElevatedButton(
        modifier = modifier
            .zIndex(1f)
            .size(70.dp)
            .offset(y = 35.dp),
        shape = CircleShape,
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(defaultElevation = 8.dp),
        onClick = navigateToRun
    ) {
        Icon(
            modifier = Modifier.scale(2f),
            bitmap = ImageBitmap.imageResource(id = R.drawable.run_next),
            contentDescription = null
        )
    }
}