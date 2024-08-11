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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.Gray100
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.LightGray
import com.example.saybettereducator.ui.theme.pretendardBoldFont
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@Composable
fun ProgressLearningMode1() {
    Box(
        Modifier
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = Gray5B,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .fillMaxWidth()
            .height(376.dp)
            .background(
                color = DarkGray,
                shape = RoundedCornerShape(size = 16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )  {
            Column(
                Modifier
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        color = LightGray,
                        shape = RoundedCornerShape(size = 12.dp)
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.progress_add_symbol),
                    contentDescription = null,
                    Modifier.size(32.dp)
                )
                Text(
                    text = stringResource(id = R.string.progress_learning_select_symbol),
                    modifier = Modifier.padding(top = 16.dp),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 22.4.sp,
                        fontFamily = FontFamily(pretendardMediumFont),
                        fontWeight = FontWeight(500),
                        color = Gray5B,
                        textAlign = TextAlign.Center,
                    )
                )
            }
            Text(
                text = "-",
                modifier = Modifier.padding(top = 18.dp),
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 25.2.sp,
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