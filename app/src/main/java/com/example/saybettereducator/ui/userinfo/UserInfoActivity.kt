package com.example.saybettereducator.ui.userinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.montserratFont

class UserInfoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserInfoScreen()
        }
    }

    @Preview(showBackground = true, widthDp = 360, heightDp = 800)
    @Composable
    fun UserInfoScreenPreview() {
        UserInfoScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun UserInfoScreen() {
        Scaffold(
            topBar = {
                Box {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Say Better",
                                fontSize = 16.sp,
                                fontFamily = FontFamily(montserratFont),
                                color = MainGreen,
                                modifier = Modifier
                                    .padding(start = 34.04.dp, top = 15.29.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.main_logo),
                        contentDescription = "main top bar logo",
                        modifier = Modifier
                            .padding(start = 16.dp, top = 14.29.dp)
                    )
                }
            },

            bottomBar = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(88.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(16.dp)
                            .background(
                                color = Color(0xFF5FD399),
                                shape = RoundedCornerShape(size = 32.dp)
                            )
                    ) {
                        Text(
                            text = "다음",
                            style = TextStyle(
                                fontSize = 18.sp,
                                lineHeight = 25.2.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                                color = Color(0xFFFFFFFF),
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(start = 16.dp, top = 20.dp, end = 16.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 50.dp)
                ) {
                    TitleText()
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    ProfileImageCard()
                }

                Column(
                    modifier = Modifier
                        .padding(top = 50.dp)
                ) {
                    Text(
                        text = "이름",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 18.2.sp,
                            fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF5B5B5B),
                        ),
                        modifier = Modifier
                            .padding(bottom = 12.dp)
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = Color(0xFF5B5B5B),
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                            .background(
                                color = Color(0xFFFFFFFF),
                                shape = RoundedCornerShape(size = 12.dp)
                            )
                            .fillMaxWidth()
                            .height(48.dp)
                    ) {
                        Text(
                            text = "교육자",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 22.4.sp,
                                fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                                color = colorResource(R.color.black),
                            ),
                            modifier = Modifier
                                .padding(start = 16.dp)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.cancel_button),
                            contentDescription = "cancell button",
                            contentScale = ContentScale.None,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .align(Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun TitleText() {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .width(187.dp)
                .height(47.dp)
        ) {
            Text(
                text = "로그인에 성공했어요!",
                fontSize = 18.sp,
                lineHeight = 25.2.sp,
                fontFamily = FontFamily(Font(R.font.pretendard_bold)),
                modifier = Modifier
                    .width(150.dp)
                    .height(24.dp)
            )

            Text(
                text = "시작하기 전 기본 설정이 필요해요.",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontFamily = FontFamily(Font(R.font.pretendard_medium)),
                    color = Color(0xFF5B5B5B),
                ),
                modifier = Modifier
                    .width(187.dp)
                    .height(18.dp)
            )
        }
    }

    @Composable
    fun ProfileImageCard() {
        val showInputPopup = remember {
            mutableStateOf(false)
        }

        Box(
            modifier = Modifier
                .padding(0.dp)
                .size(176.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.educator_profile),
                contentDescription = "Educator Profile Image",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = colorResource(R.color.grey_100),
                        shape = RoundedCornerShape(size = 24.dp)
                    )
                    .size(160.dp)
                    .clip(RoundedCornerShape(size = 24.dp))
            )

            Image(
                painter = painterResource(id = R.drawable.profile_button),
                contentDescription = "Profile Button",
                contentScale = ContentScale.None,
                modifier = Modifier
                    .padding(0.dp)
                    .size(44.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(50))
                    .clickable { showInputPopup.value = true }
            )

            if (showInputPopup.value) {
                ProfileInputPopup(showInputPopup)
            }
        }

    }

    @Composable
    private fun ProfileInputPopup(showInputPopup: MutableState<Boolean>) {
        Popup(
            alignment = Alignment.Center,
            onDismissRequest = { showInputPopup.value = false }
        ) {
            Surface(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                color = Color.White
            ) {
                // Popup content goes here
                Box(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = "Popup Content")
                }
            }
        }
    }
}
