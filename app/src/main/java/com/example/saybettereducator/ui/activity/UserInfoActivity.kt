package com.example.saybettereducator.ui.activity

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.saybettereducator.ui.intent.UserInfoIntent
import com.example.saybettereducator.ui.view.screen.LoginSuccessScreen
import com.example.saybettereducator.ui.view.screen.UserInfoScreen
import com.example.saybettereducator.ui.viewmodel.UserInfoViewModel
import kotlinx.coroutines.launch
import java.io.File

class UserInfoActivity : ComponentActivity() {
    private val viewModel: UserInfoViewModel by viewModels()
    private lateinit var cameraImageUri: Uri

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.processIntent(UserInfoIntent.UpdateProfileImage(cameraImageUri))
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                viewModel.processIntent(UserInfoIntent.UpdateProfileImage(it))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController, startDestination = "userInfoScreen") {
                composable("userInfoScreen") { UserInfoScreen(navController, viewModel) }
                composable("loginSuccessScreen") { LoginSuccessScreen(navController) }
            }
        }

        lifecycleScope.launch {
            viewModel.state.collect { state ->
                if (state.openCamera) {
                    openCamera()
                    viewModel.processIntent(UserInfoIntent.UpdateOpenCamera(false))
                }
                if (state.openGallery) {
                    openGallery()
                    viewModel.processIntent(UserInfoIntent.UpdateOpenGallery(false))
                }
            }
        }
    }

    private fun openCamera() {
        val imageFile = File.createTempFile("profile_image", ".jpg", cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        cameraImageUri = FileProvider.getUriForFile(this, "$packageName.fileprovider", imageFile)
        cameraLauncher.launch(cameraImageUri)
    }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }
}
