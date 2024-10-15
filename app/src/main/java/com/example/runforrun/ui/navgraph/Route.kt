package com.example.runforrun.ui.navgraph

import androidx.navigation.navDeepLink

sealed class Route(val route: String) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object ProfileScreen : Route(route = "profileScreen")
    data object RunNavigation : Route(route = "runNavigation")
    data object StartRunNavigator : Route(route = "startRunNavigator")
    data object RunScreen : Route(route = "runScreen")
    data object AllRunsScreen : Route(route = "allRunsScreen")
    data object RunningStatisticsScreen : Route(route = "runningStatisticsScreen")

    data object CurrentRun : Route("runScreen") {
        val currentRunUri = "https://runforrun.example.com/$route"
        val deepLinks = listOf(
            navDeepLink {
                uriPattern = currentRunUri
            }
        )
    }
}

