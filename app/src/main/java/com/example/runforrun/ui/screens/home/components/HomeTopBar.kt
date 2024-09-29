package com.example.runforrun.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.runforrun.data.model.User
import kotlin.math.roundToInt

@Composable
fun HomeTopBar(
    modifier: Modifier = Modifier,
    user: User,
    weeklyGoal: Float,
    onWeeklyGoalClick: () -> Unit,
    distanceCovered: Float
) {
    Box(modifier = modifier.height(IntrinsicSize.Min)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                )
        )
        Column(
            modifier = Modifier.padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            HomeTopBarHeader(user = user)
            Spacer(modifier = Modifier.size(32.dp))
            HomeWeeklyGoalCard(
                modifier = Modifier.offset(y = 24.dp),
                weeklyGoal = weeklyGoal.roundToInt(),
                weeklyGoalDone = distanceCovered,
                onClick = onWeeklyGoalClick
            )
        }
    }
}