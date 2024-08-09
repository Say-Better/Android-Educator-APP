package com.example.saybettereducator.ui.view.components.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.montserratFont

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun UserInfoTopAppBar() {
    Box {
        TopAppBar(
            title = {
                Text(
                    text = "Say Better",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(montserratFont),
                    color = MainGreen,
                    modifier = Modifier.padding(start = 34.04.dp, top = 15.29.dp)
                )
            }, modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        )
        Image(
            painter = painterResource(id = R.drawable.main_logo),
            contentDescription = "main top bar logo",
            modifier = Modifier.padding(start = 16.dp, top = 14.29.dp)
        )
    }
}