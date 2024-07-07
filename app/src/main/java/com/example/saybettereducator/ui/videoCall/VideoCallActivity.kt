package com.example.saybettereducator.ui.videoCall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.DeepDarkGray
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.MainGreen_85
import com.example.saybettereducator.ui.theme.pretendardMediumFont

class VideoCallActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VideoCallView()
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun VideoCallView() {
        Scaffold(
            topBar = {
                Box {
                    TopAppBar(
                        title = {
                            Text(
                                text = "홍길동 학습자와 솔루션 진행",
                                fontSize = 18.sp,
                                fontFamily = FontFamily(pretendardMediumFont),
                                color = Color.White,
                                modifier = Modifier.padding(top = 15.dp, start = 32.dp)
                            )
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = DeepDarkGray
                        ),
                        modifier = Modifier
                            .height(56.dp)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.solution_back_arrow),
                        contentDescription = "솔루션 종료 버튼",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(top = 15.dp, start = 16.dp)
                            .clickable { /* Todo: 솔루션 종료 버튼 구현 */ },
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.dot_menu),
                        contentDescription = "혹시 있을지도 모르는 자잘한 설정 항목",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 15.dp, end = 16.dp)
                            .clickable { /* Todo: 설정 항목 구현 */ }
                    )
                }
            }
        ) { innerPadding ->
            Surface(color = Color.Black) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.educator_cam),
                        contentDescription = "교육자 캠",
                        modifier = Modifier
                            .padding(top = 53.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .width(328.dp)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.learner_cam),
                        contentDescription = "학습자 캠",
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .width(328.dp)
                    )
                    Box(
                        modifier = Modifier.padding(top = 35.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.hellobtn),
                            contentDescription = null,
                            tint = MainGreen,
                            modifier = Modifier
                                .width(72.dp)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.hello),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .padding(start = 18.dp, top = 18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    SolutionBottomBar()
                }
            }
        }
    }

    private @Composable
    fun SolutionBottomBar() {
        Surface(
            color = DarkGray,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(109.dp)
        ){
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 37.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.mic),
                        contentDescription = "mic on/off",
                        tint = Color.White,
                        modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cam),
                        contentDescription = "mic on/off",
                        tint = Color.White,
                        modifier = Modifier.padding(top = 14.dp, start = 10.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(28.dp))
                        .alpha(0.8f)
                        .background(MainGreen)
                        .size(width = 152.dp, height = 40.dp)
                        .clickable {  },
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.solution_start_btn_icon),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.padding(start = 21.5.dp)
                    )
                    Text(
                        text = "솔루션 시작",
                        fontSize = 16.sp,
                        color = Color.White,
                        fontFamily = FontFamily(pretendardMediumFont),
                        modifier = Modifier.padding(end = 21.5.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(width = 40.dp, height = 40.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cam_switch),
                        contentDescription = "mic on/off",
                        tint = Color.White,
                        modifier = Modifier.padding(top = 11.dp, start = 10.dp)
                    )
                }
            }
        }
    }

    @Preview(widthDp = 360, heightDp = 800)
    @Composable
    fun VideoCallViewPreview() {
        VideoCallView()
    }

}