package com.example.runforrun.data.repository

import androidx.core.net.toUri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.runforrun.data.model.Gender
import com.example.runforrun.data.model.User
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val dateStore: DataStore<Preferences>
) {
    companion object {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_WEIGHT = floatPreferencesKey("user_weight")
        val USER_WEEKLY_GOAL = floatPreferencesKey("user_weekly_goal")
        val USER_IMAGE = stringPreferencesKey("user_image")
    }

    val user = dateStore.data.map {
        val dbImage = it[USER_IMAGE]
        User(
            name = it[USER_NAME] ?: "",
            gender = Gender.valueOf(it[USER_GENDER] ?: Gender.OTHER.name),
            weight = it[USER_WEIGHT] ?: 0.0f,
            weeklyGoal = it[USER_WEEKLY_GOAL] ?: 0.0f,
            img = if (dbImage.isNullOrBlank()) null else dbImage.toUri()
        )
    }

    suspend fun updateUser(user: User) = dateStore.edit {
        it[USER_NAME] = user.name
        it[USER_GENDER] = user.gender.name
        it[USER_WEIGHT] = user.weight
        it[USER_WEEKLY_GOAL] = user.weeklyGoal
        it[USER_IMAGE] = user.img?.toString() ?: ""
    }

    val doesUserExist = dateStore.data.map { it[USER_NAME] != null }
}