package com.example.runforrun.ui.screens.run.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.utils.FormatterUts.getFormattedTime
import com.example.runforrun.domain.model.CurrentRunningAndCalories

@Composable
fun RunningStatsCard(
    modifier: Modifier = Modifier,
    duration: Long = 0L,
    state: CurrentRunningAndCalories,
    playOrPauseClick: () -> Unit,
    finish: () -> Unit
) {
    ElevatedCard(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                val runningTimeText = stringResource(id = R.string.running_time)
                Text(
                    text = runningTimeText,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = getFormattedTime(duration),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
            Row(
                modifier = modifier
            ) {
                if (!state.currentRunning.tracking && duration > 0) {
                    IconButton(
                        onClick = finish,
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = CircleShape
                            )
                    ) {
                        Icon(
                            bitmap = ImageBitmap.imageResource(id = R.drawable.finish),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
                IconButton(
                    onClick = playOrPauseClick,
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                ) {
                    Icon(
                        bitmap = ImageBitmap.imageResource(
                            id = if (state.currentRunning.tracking) R.drawable.pause else R.drawable.play
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        val km = stringResource(id = R.string.km)
        val kcal = stringResource(id = R.string.kcal)
        val kmHr = stringResource(id = R.string.km_hr)
        Row(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(id = R.drawable.run_next),
                unit = km,
                value = (state.currentRunning.distance / 1000f).toString()
            )
            VerticalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(id = R.drawable.fire),
                unit = kcal,
                value = state.caloriesBurned.toString()
            )
            VerticalDivider(
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            RunningStatsItem(
                bitmap = ImageBitmap.imageResource(id = R.drawable.bolt),
                unit = kmHr,
                value = state.currentRunning.speed.toString()
            )
        }
    }
}