package com.example.saybettereducator.utils.image

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.core.content.FileProvider
import java.io.File

class CameraUtil(private val context: Context) {
    private lateinit var cameraImageUri: Uri

    @Composable
    fun openCamera(onTakePicture: (Uri) -> Unit) {
        val imageFile = File.createTempFile("profile_image", ".jpg", context.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }
        cameraImageUri =
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", imageFile)
        takePicture(onTakePicture).launch(cameraImageUri)
    }

    @Composable
    fun takePicture(onTakePicture: (Uri) -> Unit) =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                onTakePicture(cameraImageUri)
            }
        }
}

