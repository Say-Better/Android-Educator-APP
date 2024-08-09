package com.example.saybettereducator.ui.view.components.userinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.PretendardTypography
import com.example.saybettereducator.ui.theme.White

@Composable
fun UserInfoBottomBar(
    text: String,
    onBottomBarClick: () -> Unit
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
            .clickable (onClick = onBottomBarClick)
        ) {
            Text(
                text = text,
                style = PretendardTypography.buttonLarge.copy(White),
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

