package com.example.runforrun.domain.usecases

import com.example.runforrun.data.repository.UserRepository
import com.example.runforrun.domain.common.utils.RunUts
import com.example.runforrun.domain.model.CurrentRunningAndCalories
import com.example.runforrun.domain.tracking.Tracking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.roundToInt

@Singleton
class GetCurrentRunningAndCaloriesUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val tracking: Tracking
) {
    operator fun invoke(): Flow<CurrentRunningAndCalories> {
        return combine(userRepository.user, tracking.currentRunning) { user, runState ->
            CurrentRunningAndCalories(
                currentRunning = runState,
                caloriesBurned = RunUts.calculateCaloriesBurned(
                    distance = runState.distance,
                    weight = user.weight
                ).roundToInt()
            )
        }
    }
}