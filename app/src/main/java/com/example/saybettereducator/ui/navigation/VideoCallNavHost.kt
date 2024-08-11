package com.example.saybettereducator.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.saybettereducator.ui.view.ProgressScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VideoCallNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = "videoCall"
) {
    NavHost(navController = navController, startDestination = startDestination) {
        composable("progress") { ProgressScreen() }
    }
}

/**
 * VideoCallActivity에 대한 Navigation Destination을 정의합니다.
 * @param url: 각 Destination에 대한 URL입니다.
 */
sealed class VideoCallNavDestinations(val url: String) {
    object VideoCall: VideoCallNavDestinations("video")
    object Progress: VideoCallNavDestinations("progress")
}