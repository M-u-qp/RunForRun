package com.example.runforrun.ui.nav

sealed class Route(val route: String) {

    data object OnBoardingScreen : Route(route = "onBoardingScreen")
    data object HomeScreen : Route(route = "homeScreen")
    data object AppStartNavigation : Route(route = "appStartNavigation")
    data object ProfileScreen : Route(route = "profileScreen")
    data object RunNavigation : Route(route = "runNavigation")
}