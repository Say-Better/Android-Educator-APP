package com.example.saybettereducator.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.SaybetterEducatorTheme
import com.example.saybettereducator.ui.theme.montserratFont
import com.example.saybettereducator.ui.theme.pretendardMediumFont

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    @Preview(widthDp = 360, heightDp = 800)
    @Composable
    fun MainScreenPreview() {
        MainScreen()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val scrollState = rememberScrollState()

        Scaffold(
            topBar = {
                Box {
                    TopAppBar(
                        title = { Text(
                            text = "Say Better",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(montserratFont),
                            color = MainGreen,
                            modifier = Modifier
                                .padding(start = 34.04.dp, top = 15.29.dp)
                        ) },
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
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(scrollState)
            ) {

            }
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
                contentDescription = "새 소식 알림 버튼",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
        }
    }
}