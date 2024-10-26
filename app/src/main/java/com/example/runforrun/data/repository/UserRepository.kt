package com.example.runforrun.data.repository

import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.runforrun.common.utils.AchievementUts
import com.example.runforrun.data.model.Gender
import com.example.runforrun.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_WEIGHT = floatPreferencesKey("user_weight")
        val USER_WEEKLY_GOAL = floatPreferencesKey("user_weekly_goal")
        val USER_IMG = stringPreferencesKey("user_img")

        val ACHIEVEMENTS_MASK_KEY = intPreferencesKey("achievements_mask_key")
    }

    //Пользователь
    val user = dataStore.data.map {
        val dbImg = it[USER_IMG]
        User(
            name = it[USER_NAME] ?: "",
            gender = Gender.valueOf(it[USER_GENDER] ?: Gender.OTHER.name),
            weight = it[USER_WEIGHT] ?: 0.0f,
            weeklyGoal = it[USER_WEEKLY_GOAL] ?: 0.0f,
            img = if (dbImg.isNullOrBlank()) null else dbImg.toUri()
        )
    }

    val doesUserExist = dataStore.data.map {
        it[USER_NAME] != null
    }

    suspend fun updateUser(user: User) = dataStore.edit {
        it[USER_NAME] = user.name
        it[USER_GENDER] = user.gender.name
        it[USER_WEEKLY_GOAL] = user.weeklyGoal
        it[USER_WEIGHT] = user.weight
        it[USER_IMG] = user.img?.toString() ?: ""
    }

    //Достижения
    suspend fun unlockAchievement(achievement: AchievementUts.Achievement) {
        dataStore.edit { preferences ->
            val currentMask = preferences[ACHIEVEMENTS_MASK_KEY] ?: 0
            preferences[ACHIEVEMENTS_MASK_KEY] = currentMask or (1 shl achievement.ordinal)
        }
    }
    fun isAchievementUnlock(achievement: AchievementUts.Achievement): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            val currentMask = preferences[ACHIEVEMENTS_MASK_KEY] ?: 0
            (currentMask and (1 shl achievement.ordinal)) != 0
        }
    }
}