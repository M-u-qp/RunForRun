package com.example.runforrun.ui.screens.achievements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.runforrun.R
import com.example.runforrun.common.utils.AchievementUts
import com.example.runforrun.ui.components.DefaultTopBar
import com.example.runforrun.ui.screens.achievements.components.AchievementCard

@Composable
fun AchievementsScreen(
    viewModel: AchievementsViewModel = hiltViewModel(),
    navigateUp: () -> Unit
) {
    val runningState = viewModel.runningState.collectAsStateWithLifecycle()
    val achievementsState by viewModel.achievementsState.collectAsStateWithLifecycle()

    val currentDistanceText = stringResource(id = R.string.ran_through)
    val currentCaloriesText = stringResource(id = R.string.burned_calories)
    val km = stringResource(id = R.string.km)
    val kcal = stringResource(id = R.string.kcal)

    LaunchedEffect(key1 = runningState.value) {
        viewModel.checkAndUnlockAchievements()
    }

    if (achievementsState.isNotEmpty()) {
        Scaffold(
            topBar = {
                DefaultTopBar(
                    navigateUp = navigateUp,
                    title = stringResource(id = R.string.achievements)
                )
            }
        ) { paddingValues ->
            Spacer(modifier = Modifier.size(32.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier
                    .padding(paddingValues = paddingValues)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                AchievementUts.Achievement.entries.forEachIndexed { index, achieve ->
                    item(index) {
                        val isUnlocked = achievementsState.getOrNull(index) ?: false
                        val currentCompletion = when (achieve) {
                            AchievementUts.Achievement.MEDAL, AchievementUts.Achievement.MEDAL_1, AchievementUts.Achievement.MEDAL_STAR ->
                                "$currentDistanceText: ${runningState.value.totalDistance}$km"

                            AchievementUts.Achievement.BOWL ->
                                "$currentCaloriesText: ${runningState.value.totalCaloriesBurned}$kcal"

                            AchievementUts.Achievement.GOAL ->
                                ""
                        }
                        AchievementCard(
                            imageBitmap = ImageBitmap.imageResource(achieve.resId),
                            goalText = stringResource(id = achieve.descriptionId),
                            currentCompletion = currentCompletion,
                            isUnlocked = isUnlocked
                        )
                    }
                }
            }
        }
    }
}