package com.example.saybettereducator.ui.home

import android.inputmethodservice.Keyboard
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.ui.theme.BoxBackground

@Composable
fun HomeScreen() {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BoxBackground
    ) {
        Column(
            Modifier.verticalScroll(scrollState)
        ) {
            HomeScheduleView()

            HomeSolutionView()
        }
    }
}

@Composable
fun HomeScheduleView() {
    Column {
        Row {
            // 이번 주 일정
            // 전체보기 버튼
        }
        HomeMiniCalendar()
    }
}

@Composable
fun HomeMiniCalendar() {
//    Image(painter = , contentDescription = )
}

@Composable
fun HomeSolutionView() {
    Column {
        // 학습자 솔루션 관리

        // 학습자 수만큼 루프
        HomeSolutionViewElement()
//        Spacer(modifier = ) 흰색!
    }
}

@Composable
fun HomeSolutionViewElement() {
    Column {
        Row {
            LearnerMiniProfile()
            CreateSolutionButton()
        }
        SolutionCardScroll()
    }
}

@Composable
fun SolutionCardScroll() {
    // 좌, 우 스크롤
    Row {
        // 솔루션 수만큼 루프
        SolutionCard()
//        Spacer(modifier = )
    }
}

@Composable
fun SolutionCard() {
    // 그림자 효과 넣기!
    TODO("Not yet implemented")
}

@Composable
fun CreateSolutionButton() {
    TODO("Not yet implemented")
}

@Composable
fun LearnerMiniProfile() {
    TODO("Not yet implemented")
}

@Preview(widthDp = 360, heightDp = 680)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}