package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.HighlightBorder
import com.example.saybettereducator.ui.theme.montserratFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressTopBar(
    isPlaying: Boolean,
    isScreenCasting: Boolean,
    onClickScreenCasting: () -> Unit
) {
    val iconTint = if (isPlaying) HighlightBorder else Gray5B
    Box(
        modifier = Modifier
            .height(60.dp)
            .fillMaxWidth()
            .background(color = Color.Black)
    ) {
        TopAppBar(
            title = {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        text = "감정표현 상황 솔루션",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(montserratFont),
                        color = Color.White,
                        lineHeight = 28.sp,
                        fontWeight = FontWeight(600),
                    )
                    Text(
                        text = "중재 단계 5회기",
                        fontSize = 12.sp,
                        lineHeight = 15.6.sp,
                        fontFamily = FontFamily(montserratFont),
                        color = Gray5B,
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Black
            )
        )

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 15.dp, end = 16.dp)
        ) {
            Icon(
                painter =
                if(!isScreenCasting)
                    painterResource(id = R.drawable.ic_start_screen_share)
                else
                    painterResource(id = R.drawable.ic_stop_screen_share),
                contentDescription = "screen share",
                tint = Color.White,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(width = 30.dp, height = 20.dp)
                    .clickable { onClickScreenCasting() }
            )

            Icon(
                painter = painterResource(id = R.drawable.ic_progress_volume),
                contentDescription = "솔루션 진행 음성 표시",
                tint = iconTint
            )
        }
    }
}