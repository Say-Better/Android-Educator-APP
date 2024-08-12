package com.example.saybettereducator.ui.navigation

import android.Manifest
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saybettereducator.ui.view.CalendarScreen
import com.example.saybettereducator.ui.view.HomeScreen
import com.example.saybettereducator.ui.view.LearnerScreen
import com.example.saybettereducator.ui.view.SolutionScreen
import com.example.saybettereducator.ui.view.UserInfoScreen
import com.example.saybettereducator.utils.permission.checkAndRequestPermissions

/**
 * App의 Navigation을 정의합니다.
 * Navigation을 위한 NavHost를 정의하고, 각 Destination에 대한 Composable을 정의합니다.
 * @param navController: App의 Navigation을 제어하는 Controller입니다.
 */
@Composable
fun AppNavHost(navController: NavHostController, startVideoCall: (String, Boolean) -> Unit) {
    val context = LocalContext.current

    val permissions = arrayOf(
        Manifest.permission.RECORD_AUDIO,
        Manifest.permission.CAMERA
    )

    val launcherMultiplePermissions = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissionsMap ->
        val areGranted = permissionsMap.values.reduce { acc, next -> acc && next }
        if (areGranted) {
            Log.d("test5", "Permissions granted.")
        } else {
            Log.d("test5", "Permissions denied.")
        }
    }

    NavHost(navController = navController, startDestination = NavDestinations.Home.url) {
        composable(NavDestinations.Home.url) {
            HomeScreen(
                onClickSolution = {
                    checkAndRequestPermissions(
                        context,
                        permissions,
                        launcherMultiplePermissions,
                        onPermissionsGranted = {
                            Log.d("permission", "Permission Granted, Go VideoCall!")
                            startVideoCall("helloYI", true)  // 여기서 적절한 값을 전달합니다.
                        }
                    )
                }
            )
        }

        composable(NavDestinations.Learner.url) {
            LearnerScreen()
        }

        composable(NavDestinations.Calendar.url) {
            CalendarScreen()
        }

        composable(NavDestinations.Solution.url) {
            SolutionScreen()
        }

        composable(NavDestinations.UserInfo.url) {
            UserInfoScreen(navController)
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
    object UserInfo : NavDestinations("user_info")
}