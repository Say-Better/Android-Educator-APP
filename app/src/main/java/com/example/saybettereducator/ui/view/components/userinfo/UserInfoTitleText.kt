package com.example.saybettereducator.ui.view.components.userinfo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.PretendardTypography

@Composable
fun UserInfoTitleText(title: String, subtitle: String) {
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