package com.example.saybettereducator.ui.components.session

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.data.repository.MainServiceRepository
import com.example.saybettereducator.ui.theme.DeepDarkGray
import com.example.saybettereducator.ui.theme.pretendardMediumFont
import com.example.saybettereducator.utils.customClick.CustomClickEvent

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadyTopBar(serviceRepository: MainServiceRepository) {
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
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = CustomClickEvent
                ) {
                    serviceRepository.sendEndCall()
                },
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