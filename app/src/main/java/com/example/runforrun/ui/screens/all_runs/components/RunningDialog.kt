package com.example.runforrun.ui.screens.all_runs.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.utils.FormatterUts.getFormattedDate
import com.example.runforrun.common.utils.FormatterUts.getFormattedTime
import com.example.runforrun.data.model.Run
import com.example.runforrun.ui.screens.run.components.RunningStatsItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RunningDialog(
    run: Run,
    dismiss: () -> Unit,
    delete: (Run) -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = dismiss,
        modifier = Modifier
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large
            )
    ) {
        Column(
            modifier = Modifier.clip(shape = MaterialTheme.shapes.large)
        ) {
            RunningRouteImage(
                run = run,
                dismiss = dismiss,
                delete = delete
            )
            Spacer(modifier = Modifier.size(20.dp))
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = getFormattedDate(run.timestamp),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Spacer(modifier = Modifier.size(10.dp))
                Text(
                    text = getFormattedTime(run.duration),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
            RunningStats(
                run = run,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}

@Composable
private fun RunningRouteImage(
    modifier: Modifier = Modifier,
    run: Run,
    dismiss: () -> Unit,
    delete: (Run) -> Unit
) {
    Box(modifier = modifier) {
        Image(
            bitmap = run.image.asImageBitmap(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        IconButton(
            onClick = dismiss,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(6.dp)
        ) {
            Icon(
                bitmap = ImageBitmap.imageResource(R.drawable.close),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 6.dp, end = 6.dp)
        ) {
            IconButton(
                onClick = { delete(run) },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.errorContainer,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.delete),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onErrorContainer
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            IconButton(
                onClick = { },
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.share),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
private fun RunningStats(
    modifier: Modifier = Modifier,
    run: Run
) {
    val km = stringResource(id = R.string.km)
    val kcal = stringResource(id = R.string.kcal)
    val kmHr = stringResource(id = R.string.km_hr)

    Row(
        modifier = modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        RunningStatsItem(
            bitmap = ImageBitmap.imageResource(id = R.drawable.go_run),
            unit = km,
            value = (run.distance / 1000f).toString()
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
        RunningStatsItem(
            bitmap = ImageBitmap.imageResource(id = R.drawable.fire),
            unit = kcal,
            value = run.caloriesBurned.toString()
        )
        Box(
            modifier = Modifier
                .width(1.dp)
                .fillMaxHeight()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterVertically)
                .background(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
        )
        RunningStatsItem(
            bitmap = ImageBitmap.imageResource(id = R.drawable.bolt),
            unit = kmHr,
            value = run.avgSpeed.toString()
        )
    }
}