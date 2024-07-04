package com.example.saybettereducator.ui.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen()
        }
    }

    @Preview
    @Composable
    fun LoginPreview() {
        LoginScreen()
    }

    @Composable
    fun LoginScreen() {
        Surface {
            Column {
                Text(text = "만나서 반가워요!\nSay Better를 시작해볼까요?")

                Image(painter = painterResource(id = R.drawable.), contentDescription = )
            }
        }
    }
}

