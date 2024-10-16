package com.example.runforrun.ui.navigation

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.runforrun.R
import com.example.runforrun.ui.navgraph.Route
import com.example.runforrun.ui.navigation.components.BottomNavigationItem
import com.example.runforrun.ui.navigation.components.RunBottomNavigation
import com.example.runforrun.ui.screens.all_runs.AllRunsScreen
import com.example.runforrun.ui.screens.all_runs.AllRunsViewModel
import com.example.runforrun.ui.screens.home.HomeScreen
import com.example.runforrun.ui.screens.home.HomeViewModel
import com.example.runforrun.ui.screens.profile.ProfileScreen
import com.example.runforrun.ui.screens.profile.ProfileViewModel
import com.example.runforrun.ui.screens.run.RunScreen
import com.example.runforrun.ui.screens.run.RunViewModel
import com.example.runforrun.ui.screens.settings.SettingsScreen
import com.example.runforrun.ui.screens.settings.SettingsViewModel
import com.example.runforrun.ui.screens.statistics.RunningStatisticsScreen
import com.example.runforrun.ui.screens.statistics.RunningStatisticsViewModel

@Composable
fun RunNavigator() {
    val viewModel: RunNavigatorViewModel = hiltViewModel()
    val doesUserExist by viewModel.doesUserExist.collectAsStateWithLifecycle()
    val homeText = stringResource(id = R.string.home)
    val profileText = stringResource(id = R.string.profile)
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.home, text = homeText),
            BottomNavigationItem(icon = R.drawable.profile, text = profileText)
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
        bottomBar = {
            if (isBottomBarVisible && doesUserExist == true) {
                RunBottomNavigation(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(64.dp))
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(64.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                        .fillMaxWidth(),
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
    ) { paddingValues ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            CompositionLocalProvider(value = compositionLocalOf { 0.dp } provides paddingValues.calculateBottomPadding()) {
                NavHost(
                    navController = navController,
                    startDestination = Route.HomeScreen.route
                ) {
                    composable(route = Route.HomeScreen.route) {
                        val homeViewModel: HomeViewModel = hiltViewModel()
                        HomeScreen(
                            viewModel = homeViewModel,
                            navigateToRun = { navController.navigate(Route.RunScreen.route) },
                            navigateToAllRuns = { navController.navigate(Route.AllRunsScreen.route) },
                            navigateToSettings = { navController.navigate(Route.SettingsScreen.route) },
                            paddingValues = paddingValues
                        )
                    }
                    composable(route = Route.ProfileScreen.route) {
                        val profileViewModel: ProfileViewModel = hiltViewModel()
                        ProfileScreen(
                            viewModel = profileViewModel,
                            profileEvent = profileViewModel,
                            paddingValues = paddingValues,
                            navigateToRunningStatistics = { navController.navigate(Route.RunningStatisticsScreen.route) },
                            navigateToSettings = { navController.navigate(Route.SettingsScreen.route) }
                        )
                    }
                    composable(
                        route = Route.RunScreen.route,
                        deepLinks = Route.CurrentRun.deepLinks
                    ) {
                        val runViewModel: RunViewModel = hiltViewModel()
                        RunScreen(
                            viewModel = runViewModel,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = Route.AllRunsScreen.route) {
                        val allRunsViewModel: AllRunsViewModel = hiltViewModel()
                        AllRunsScreen(
                            viewModel = allRunsViewModel,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = Route.RunningStatisticsScreen.route) {
                        val runningStatisticsViewModel: RunningStatisticsViewModel = hiltViewModel()
                        RunningStatisticsScreen(
                            viewModel = runningStatisticsViewModel,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                    composable(route = Route.SettingsScreen.route) {
                        val settingsViewModel: SettingsViewModel = hiltViewModel()
                        SettingsScreen(
                            viewModel = settingsViewModel,
                            navigateUp = { navController.navigateUp() }
                        )
                    }
                }
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