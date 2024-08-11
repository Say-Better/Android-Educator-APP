package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.Gray100
import com.example.saybettereducator.ui.theme.pretendardBoldFont

@Composable
fun ProgressLearningMode3(modifier: Modifier = Modifier) {
    Box(
        modifier
            .border(
                width = 1.dp,
                color = Gray100,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .height(205.dp)
            .background(
                color = DarkGray,
                shape = RoundedCornerShape(size = 16.dp)
            ),
        contentAlignment = Alignment.Center // Box 내에서 중앙 정렬
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .height(138.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(size = 12.dp)
                    ),
                contentAlignment = Alignment.Center // Image를 중앙에 배치
            ) {
                Image(
                    painter = painterResource(id = R.drawable.progress_add_symbol),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp) // 이미지 크기 고정
                )
            }
            Text(
                text = "-",
                modifier = Modifier.padding(top = 18.dp),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 18.2.sp,
                    fontFamily = FontFamily(pretendardBoldFont),
                    fontWeight = FontWeight(500),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                )
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}