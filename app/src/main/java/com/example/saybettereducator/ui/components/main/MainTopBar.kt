package com.example.saybettereducator.ui.components.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.montserratFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopBar() {
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
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
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
        TopBarNavigation(
            Modifier
                .fillMaxWidth()
                .padding(top = 14.dp, end = 16.dp)
        )
    }
}

@Composable
fun TopBarNavigation(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.End
    ) {
        Image(
            painter = painterResource(id = R.drawable.alarm_bell),
            contentDescription = "새 소식 알림 버튼",
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(17.dp))
        Image(
            painter = painterResource(id = R.drawable.profile_my),
            contentDescription = "교육자 프로필 버튼",
            modifier = Modifier
                .align(Alignment.CenterVertically)
        )
    }
}