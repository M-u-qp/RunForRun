package com.example.runforrun.ui.screens.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.ui.screens.profile.ProfileScreenState
import com.example.runforrun.ui.screens.run.components.RunningStatsItem

@Composable
fun ProgressCard(
    modifier: Modifier = Modifier,
    state: ProfileScreenState
) {

    val km = stringResource(id = R.string.km)
    val kcal = stringResource(id = R.string.kcal)
    val kmHr = stringResource(id = R.string.km_hr)

    ElevatedCard(
        modifier = modifier,
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.overall_progress),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.drawable.forward),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(R.drawable.go_run),
                unit = km,
                value = state.totalDistance.toString()
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
            )
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(R.drawable.fire),
                unit = kcal,
                value = state.totalCaloriesBurned.toString()
            )
            Box(
                modifier = Modifier
                    .width(1.dp)
                    .fillMaxHeight()
                    .padding(vertical = 8.dp)
                    .align(Alignment.CenterVertically)
                    .background(color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
            )
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(R.drawable.watch),
                unit = kmHr,
                value = state.totalDuration.toString()
            )
        }
    }
}