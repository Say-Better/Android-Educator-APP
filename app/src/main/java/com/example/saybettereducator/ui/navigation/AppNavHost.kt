package com.example.saybettereducator.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saybettereducator.ui.view.screen.HomeScreen

/**
 * App의 Navigation을 정의합니다.
 * Navigation을 위한 NavHost를 정의하고, 각 Destination에 대한 Composable을 정의합니다.
 * @param navController: App의 Navigation을 제어하는 Controller입니다.
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavDestinations.Home.url) {
        composable(NavDestinations.Home.url) {
            HomeScreen(navController)
        }

        composable(NavDestinations.Learner.url) {
            // LearnerScreen()
        }

        composable(NavDestinations.Calendar.url) {
            // CalendarScreen()
        }

        composable(NavDestinations.Solution.url) {
            // SolutionScreen()
        }
    }
}

/**
 * App에 대한 Navigation Destination을 정의합니다.
 * @param url: App의 각 Destination에 대한 URL입니다.
 */
sealed class NavDestinations(val url: String) {
    object Home : NavDestinations("home")
    object Learner : NavDestinations("learner")
    object Calendar : NavDestinations("calendar")
    object Solution : NavDestinations("solution")
}