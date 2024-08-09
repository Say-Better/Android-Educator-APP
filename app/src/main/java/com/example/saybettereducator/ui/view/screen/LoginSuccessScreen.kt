package com.example.saybettereducator.ui.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.view.components.userinfo.UserInfoBottomBar
import com.example.saybettereducator.ui.view.components.userinfo.UserInfoTitleText
import com.example.saybettereducator.ui.view.components.userinfo.UserInfoTopAppBar

@Composable
fun LoginSuccessScreen(
    navController: NavController, goMainActivity: (() -> Unit)? = null
) {
    Scaffold(topBar = { UserInfoTopAppBar() },
        bottomBar = {
            UserInfoBottomBar(
                "시작하기",
                onBottomBarClick = {
                    goMainActivity?.invoke()
                }
            )
        }) { innerPadding -> LoginSuccessContent(innerPadding) }
}

@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun LoginSuccessScreenPreview() {
    val navController = rememberNavController()
    LoginSuccessScreen(navController)
}

@Composable
private fun LoginSuccessContent(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(start = 16.dp, top = 20.dp, end = 16.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(bottom = 24.dp)) {
            UserInfoTitleText(
                title = "모든 준비가 완료되었어요.", subtitle = "원활한 앱 사용을 위해 앱 내 권한을 허용해주세요."
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
        ) {
            Image(
                painter = painterResource(R.drawable.login_illust_educator),
                contentDescription = "Login Illustration",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(480.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
        }
    }
}