package com.example.runforrun.ui.screens.statistics.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.runforrun.R
import com.example.runforrun.common.extension.condition
import com.example.runforrun.common.extension.setMinimumTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun DateRangeCard(
    modifier: Modifier = Modifier,
    dateList: List<Date>,
    incrementDate: () -> Unit,
    decrementDate: () -> Unit,
    isDateAvailable: (date: Date) -> Boolean
) {
    val today = remember(dateList) { Calendar.getInstance().setMinimumTime().time }
    val isIncrement = remember(dateList, today) { dateList.last() < today }
    val dayFormatter = remember { SimpleDateFormat("EEEE", Locale.getDefault()) }
    val dateFormatter = remember { SimpleDateFormat("d", Locale.getDefault()) }
    val primaryColor = MaterialTheme.colorScheme.primary
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface

    Box(
        modifier = modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.background,
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                )
            )
            .shadow(
                shape = RoundedCornerShape(
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp
                ),
                elevation = 2.dp,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = decrementDate) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.backward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            dateList.forEach { date ->
                Column(
                    modifier = Modifier.padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dayFormatter.format(date).first().toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Text(
                        text = dateFormatter.format(date).first().toString(),
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = if (isDateAvailable(date)) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        ),
                        modifier = Modifier.condition(condition = isDateAvailable(date) || date == today) {
                            Modifier.drawBehind {
                                if (isDateAvailable(date)) {
                                    drawCircle(
                                        color = primaryColor,
                                        radius = size.maxDimension / 2
                                    )
                                }
                                if (date == today) {
                                    drawCircle(
                                        color = onSurfaceColor,
                                        radius = size.maxDimension / 2,
                                        style = Stroke(width = 2.dp.toPx())
                                    )
                                }
                            }
                        }
                            .padding(4.dp)
                    )
                }
            }
            IconButton(
                onClick = incrementDate,
                enabled = isIncrement
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(id = R.drawable.forward),
                    contentDescription = null,
                    tint = if (isIncrement) {
                        MaterialTheme.colorScheme.onSurfaceVariant
                    } else {
                        LocalContentColor.current
                    }
                )
            }
        }
    }
}