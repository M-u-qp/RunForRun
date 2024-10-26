package com.example.runforrun.common.utils

import com.example.runforrun.R

object AchievementUts {
    enum class Achievement(val resId: Int, val descriptionId: Int) {
        MEDAL_1(resId = R.drawable.achieve_medal_1, descriptionId = R.string.km_10),
        MEDAL(resId = R.drawable.achieve_medal, descriptionId = R.string.km_30),
        MEDAL_STAR(resId = R.drawable.achieve_medal_star, descriptionId = R.string.km_50),
        BOWL(resId = R.drawable.achieve_bowl, descriptionId = R.string.kcal_10000),
        GOAL(resId = R.drawable.achieve_goal, descriptionId = R.string.get_all_goal)
    }
}