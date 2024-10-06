package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.utils.getFormattedDateTime
import com.example.runforrun.data.model.Run

@Composable
fun RunItem(
    modifier: Modifier = Modifier,
    run: Run,
    showDoneIcon: Boolean = true
) {
    val km = stringResource(id = R.string.km)
    val kmHr = stringResource(id = R.string.km_hr)
    val kcal = stringResource(id = R.string.kcal)

    ElevatedCard(
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Row(modifier = modifier.fillMaxWidth().padding(16.dp)) {
            Image(
                bitmap = run.image.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier.size(70.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.size(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = getFormattedDateTime(run.timestamp),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Normal
                    )
                )
                Spacer(modifier = Modifier.size(12.dp))
                Text(
                    text = "${(run.distance / 1000f)} $km",
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.size(12.dp))
                Row {
                    Text(
                        text = "${run.caloriesBurned} $kcal",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Normal
                        ),
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = "${run.avgSpeed} $kmHr",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Normal
                        ),
                    )
                }
            }
            if (showDoneIcon) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.forward),
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterVertically),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}