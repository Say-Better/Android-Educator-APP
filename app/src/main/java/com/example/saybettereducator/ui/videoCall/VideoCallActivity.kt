package com.example.saybettereducator.ui.videoCall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class VideoCallActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideoCallView()
        }
    }

    @Composable
    fun VideoCallView() {
        Text(text = "VideoCall")
    }

    @Preview
    @Composable
    fun VideoCallViewPreview() {
        VideoCallView()
    }

}