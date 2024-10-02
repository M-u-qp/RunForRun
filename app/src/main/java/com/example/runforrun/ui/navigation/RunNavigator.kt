package com.example.runforrun.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runforrun.R
import com.example.runforrun.ui.navgraph.Route
import com.example.runforrun.ui.navigation.components.BottomNavigationItem
import com.example.runforrun.ui.navigation.components.RunBottomNavigation
import com.example.runforrun.ui.screens.home.HomeScreen
import com.example.runforrun.ui.screens.home.HomeViewModel
import com.example.runforrun.ui.screens.profile.ProfileScreen
import com.example.runforrun.ui.screens.profile.ProfileViewModel
import com.example.runforrun.ui.screens.run.RunScreen
import com.example.runforrun.ui.screens.run.RunViewModel

@Composable
fun RunNavigator() {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home, text = "Дом"),
            BottomNavigationItem(icon = R.drawable.profile, text = "Профиль")
        )
    }
    val navController = rememberNavController()
    val backstackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = remember(key1 = backstackState) {
        when (backstackState?.destination?.route) {
            Route.HomeScreen.route -> 0
            Route.ProfileScreen.route -> 1
            else -> 0
        }
    }

    val isBottomBarVisible = remember(key1 = backstackState) {
        backstackState?.destination?.route == Route.HomeScreen.route ||
                backstackState?.destination?.route == Route.ProfileScreen.route
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                RunBottomNavigation(
                    items = bottomNavigationItems,
                    selected = selectedItem,
                    onItemClick = { index ->
                        when (index) {
                            0 -> navigateToTab(
                                navController = navController,
                                route = Route.HomeScreen.route
                            )

                            1 -> navigateToTab(
                                navController = navController,
                                route = Route.ProfileScreen.route
                            )
                        }
                    }
                )
            }
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = viewModel,
                    navigateToRun = { navController.navigate(Route.RunScreen.route) }
                )
            }
            composable(route = Route.ProfileScreen.route) {
                val viewModel: ProfileViewModel = hiltViewModel()
                ProfileScreen(viewModel = viewModel)
            }
            composable(
                route = Route.RunScreen.route,
                deepLinks = Route.CurrentRun.deepLinks
            ) {
                val viewModel: RunViewModel = hiltViewModel()
                RunScreen(
                    viewModel = viewModel,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}