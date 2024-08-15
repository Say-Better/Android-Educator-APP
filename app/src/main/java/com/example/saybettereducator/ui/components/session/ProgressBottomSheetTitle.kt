package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R

@Composable
fun ProgressBottomSheetTitle(
    title: Int
) {
    Text(
        text = stringResource(id = title),
        color = Color.White,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        style = TextStyle(
            fontSize = 16.sp,
            lineHeight = 20.8.sp,
            fontFamily = FontFamily(Font(R.font.pretendard_bold)),
            fontWeight = FontWeight(600),
            color = Color.White
        )
    )
    Spacer(modifier = Modifier.size(8.dp))
}