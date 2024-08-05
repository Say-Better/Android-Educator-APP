package com.example.saybettereducator.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.theme.montserratFont

@Composable
fun BottomBar(
    navController: NavController, route: String, text: String, goMainActivity: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(16.dp)
            .background(
                color = MainGreen, shape = RoundedCornerShape(size = 32.dp)
            )
            .clickable {
                if (goMainActivity == null) {
                    navController.navigate(route)
                } else {
                    goMainActivity()
                }
            }) {
            Text(
                text = text,
                style = PretendardTypography.buttonLarge.copy(White),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBar() {
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

@Composable
fun TitleText(title: String, subtitle: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = PretendardTypography.headlineMedium.copy(Black),
        )

        Text(
            text = subtitle,
            style = PretendardTypography.bodySmall.copy(GrayW40),
        )
    }
}