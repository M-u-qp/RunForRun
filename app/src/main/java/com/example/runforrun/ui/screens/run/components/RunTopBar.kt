package com.example.runforrun.ui.screens.run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R

@Composable
fun RunTopBar(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit
) {
    IconButton(
        onClick = navigateUp,
        modifier = modifier
            .size(40.dp)
            .shadow(
                elevation = 4.dp,
                shape = CircleShape,
                clip = true
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(4.dp)
    ) {
        Icon(
            bitmap = ImageBitmap.imageResource(id = R.drawable.backward),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}