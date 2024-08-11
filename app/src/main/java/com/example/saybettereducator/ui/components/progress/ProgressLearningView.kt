package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.pretendardBoldFont
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@Composable
fun ProgressLearningView(selectedMode: Int) {
    when (selectedMode) {
        1 -> {
            ProgressLearningMode1()
        }

        2 -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                ProgressLearningMode2()
                Spacer(modifier = Modifier.height(8.dp))
                ProgressLearningMode2()
            }
        }

        3 -> {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                for (i in 0..1) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        for (j in 0..1) {
                            ProgressLearningMode3(Modifier.weight(1f))
                            if (j < 1) Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    if (i < 1) Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        4 -> ProgressLearningMode4()
    }
}



@Preview
@Composable
fun ProgressLearningPreview() {
    ProgressLearningView(4)
}