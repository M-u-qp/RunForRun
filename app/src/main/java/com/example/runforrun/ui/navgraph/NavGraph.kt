package com.example.runforrun.ui.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.runforrun.ui.navigation.RunNavigator
import com.example.runforrun.ui.screens.onboard.OnBoardingScreen
import com.example.runforrun.ui.screens.onboard.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(
                route = Route.OnBoardingScreen.route
            ) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    onBoardingScreenEvent = viewModel,
                    navigateToHome = { navController.navigate(Route.RunNavigation.route) }
                )
            }
        }
        navigation(
            route = Route.RunNavigation.route,
            startDestination = Route.RunNavigationScreen.route
        ) {
            composable(
                route = Route.RunNavigationScreen.route
            ) {
                RunNavigator()
            }
        }
    }
}