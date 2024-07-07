package com.example.saybettereducator.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.MainActivity
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.pretendardMediumFont

class LoginActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LoginScreen()
        }

        overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN, R.anim.horizon_enter, R.anim.none)
    }

    @Preview(widthDp = 360, heightDp = 800)
    @Composable
    fun LoginPreview() {
        LoginScreen()
    }

    @Composable
    fun LoginScreen() {
        Surface(color = Color.White) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(top = 64.dp)
            ) {
                Text(
                    text = "만나서 반가워요!\nSay Better를 시작해볼까요?",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(pretendardMediumFont),
                    modifier = Modifier.padding(start = 19.98.dp)
                )

                Spacer(modifier = Modifier.height(30.29.dp))

                Image(
                    painter = painterResource(id = R.drawable.login_raw),
                    contentDescription = null,
                    modifier = Modifier
                        .width(360.dp)
                        .height(387.43.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "\"Say Better Life, Say Better Dream.\"",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(pretendardMediumFont),
                    color = MainGreen,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 20.dp, bottom = 66.12.dp)
                )

                LoginButton()
            }
        }
    }

    @Composable
    fun LoginButton() {
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .border((1.5).dp, Color.Black, RoundedCornerShape(100.dp))
                .background(Color.White)
                .clickable{
                    finish()
                    intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.google_logo),
                    contentDescription = "Google 소셜 로그인 버튼에 표시되는 로고"
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Google로 로그인하기",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(pretendardMediumFont)
                )
            }
        }


    }
}

