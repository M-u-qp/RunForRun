package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.example.runforrun.data.model.User
import kotlin.math.roundToInt

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    user: User,
    weeklyGoal: Float,
    onWeeklyGoalClick: () -> Unit,
    distanceCovered: Float,
    duration: Long,
    navigateToRun: () -> Unit
) {
    Box(
        modifier = modifier.height(IntrinsicSize.Min),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                )
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(bottomStart = 64.dp, bottomEnd = 64.dp)
                )
        )
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HomeTopBarHeader(user = user)
            Spacer(modifier = Modifier.size(32.dp))
            if (duration > 0) {
                HomeWeeklyGoalCard(
                    weeklyGoal = weeklyGoal.roundToInt(),
                    weeklyGoalDone = distanceCovered,
                    onClick = onWeeklyGoalClick
                )
            } else {
                RunButton(
                    modifier = Modifier,
                    navigateToRun = navigateToRun
                )
            }
        }
    }
}