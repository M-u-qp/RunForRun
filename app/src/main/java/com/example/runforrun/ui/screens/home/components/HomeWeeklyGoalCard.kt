package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.runforrun.R

@Composable
fun HomeWeeklyGoalCard(
    modifier: Modifier = Modifier,
    weeklyGoal: Int,
    weeklyGoalDone: Float,
    onClick: () -> Unit
) {
    val passed = stringResource(id = R.string.passed)
    val isLeft = stringResource(id = R.string.is_left)
    val km = stringResource(id = R.string.km)

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .offset(y = 30.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
        onClick = onClick,
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
        ) {
            Text(
                text = stringResource(id = R.string.weekly_goal),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "$weeklyGoal $km",
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.weight(1f)
            )
            Icon(
                bitmap = ImageBitmap.imageResource(id = R.drawable.run_next),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.CenterVertically),
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "$weeklyGoalDone $km $passed",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodySmall.copy(
                    )
                )
                Text(
                    text = "${
                        (weeklyGoal - weeklyGoalDone).coerceIn(
                            0f,
                            weeklyGoal.toFloat()
                        )
                    } $km $isLeft",
                    style = MaterialTheme.typography.bodySmall.copy(
                    )
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            LinearProgressIndicator(
                progress = { if (weeklyGoal > 0) weeklyGoalDone / weeklyGoal else 0f },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.inversePrimary,
                trackColor = MaterialTheme.colorScheme.onPrimary,
                strokeCap = StrokeCap.Round,
            )
        }
    }
}