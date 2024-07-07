package com.example.saybettereducator.ui.home

import android.content.Context
import android.content.Intent
import android.inputmethodservice.Keyboard
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.BoxBackground
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.GrayW90
import com.example.saybettereducator.ui.theme.pretendardBoldFont
import com.example.saybettereducator.ui.theme.pretendardMediumFont
import com.example.saybettereducator.ui.videoCall.VideoCallActivity
import com.example.saybettereducator.utils.customClick.CustomClickEvent

@Composable
fun HomeScreen(
    onClickSolution: () -> Unit
) {
    val scrollState = rememberScrollState()

    Surface(
        color = BoxBackground
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
        ) {
            HomeScheduleView()

            HomeSolutionView(onClickSolution)
        }
    }
}

@Composable
fun HomeScheduleView() {
    Column(
        modifier = Modifier.padding(top = 16.33.dp, bottom = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 13.39.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "이번 주 일정",
                fontSize = 18.sp,
                fontFamily = FontFamily(pretendardMediumFont)
            )
            Text(
                text = "전체보기",
                fontSize = 12.sp,
                color = GrayW40,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        HomeMiniCalendar()
    }
}

@Composable
fun HomeMiniCalendar() {
    Card(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(172.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Card Title", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "This is the content of the card.")
        }
    }
}

@Composable
fun HomeSolutionView(
    onClickSolution: () -> Unit
) {
    Column {
        Text(
            text = "학습자 솔루션 관리",
            fontSize = 18.sp,
            fontFamily = FontFamily(pretendardMediumFont),
            modifier = Modifier
                .padding(start = 16.dp)
                .fillMaxWidth()
        )

        // 학습자 수만큼 루프
        for (i in 1..5)
            HomeSolutionViewElement(onClickSolution)


    }
}

@Composable
fun HomeSolutionViewElement(
    onClickSolution: () -> Unit
) {
    Column(modifier = Modifier.padding(top = 18.dp)) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            LearnerMiniProfile()
            CreateSolutionButton()
        }
        SolutionCardScroll(onClickSolution)
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(Color.White)
        )
    }
}

@Composable
fun SolutionCardScroll(
    onClickSolution: () -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        Modifier
            .horizontalScroll(scrollState)
            .padding(bottom = 14.dp)
    ) {
        for (i in 1..5) {
            SolutionCard(onClickSolution)
            Spacer(modifier = Modifier.width(8.dp))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SolutionCard(
    onClickSolution: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .size(width = 152.dp, height = 152.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = CustomClickEvent
            ) { onClickSolution() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 12.dp)
        ) {
            Box {
                Image(
                    painter = painterResource(id = R.drawable.symbol_go),
                    contentDescription = "symbol sample go",
                    modifier = Modifier
                        .size(72.dp, 72.dp)
                        .offset(x = 52.dp)
                        .shadow(
                            elevation = 0.5.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                )
                Image(
                    painter = painterResource(id = R.drawable.symbol_rice),
                    contentDescription = "symbol sample rice",
                    modifier = Modifier
                        .size(72.dp, 72.dp)
                        .shadow(
                            elevation = 1.dp,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .clip(RoundedCornerShape(8.dp))
                )
            }
            Text(
                text = "학교생활 상황",
                fontSize = 14.sp,
                fontFamily = FontFamily(pretendardMediumFont),
                modifier = Modifier.padding(top = 12.dp)
            )
            Text(
                text = "중재 단계 5회기 진행중",
                fontSize = 12.sp,
                fontFamily = FontFamily(pretendardMediumFont),
                color = GrayW40
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateSolutionButton() {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = GrayW90,
            contentColor = GrayW40
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .width(107.dp)
            .height(26.dp),
        onClick = { Log.d("HomeScreen", "새 솔루션 할당 OK") }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.solution_plus),
                contentDescription = "+",
                tint = GrayW40,
                modifier = Modifier.padding(end = 4.dp)
                )
            Text(
                text = "새 솔루션 할당",
                fontFamily = FontFamily(pretendardMediumFont),
                fontSize = 12.sp,
                color = GrayW40
            )
        }
    }
}

@Composable
fun LearnerMiniProfile(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Row {
            Image(
                painter = painterResource(R.drawable.learner_profile),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(width = 48.dp, height = 48.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            ) {
                Text(
                    text = "송승아 학습자",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(pretendardMediumFont)
                )
                Text(
                    text = "14세/여자",
                    fontSize = 12.sp,
                    fontFamily = FontFamily(pretendardMediumFont),
                    color = GrayW40
                )
            }
        }
    }
}

@Preview(widthDp = 360, heightDp = 680)
@Composable
fun HomeScreenPreview() {
//    HomeScreen()
}