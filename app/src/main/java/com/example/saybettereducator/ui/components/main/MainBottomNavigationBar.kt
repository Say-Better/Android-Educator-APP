package com.example.saybettereducator.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.GrayW40
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@Composable
fun MainBottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(72.dp)
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = { navController.navigate("home") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_nav),
                    contentDescription = "bottom navigation home",
                    tint = if (currentRoute == "home") MainGreen else GrayW40
                )
            },
            label = {
                Text(
                    text = "홈",
                    style = TextStyle(
                        fontFamily = FontFamily(pretendardMediumFont),
                        color = if (currentRoute == "home") MainGreen else GrayW40,
                        fontSize = 12.sp
                    )
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == "learner",
            onClick = { navController.navigate("learner") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.learner_nav),
                    contentDescription = "bottom navigation learner",
                    tint = if (currentRoute == "learner") MainGreen else GrayW40
                )
            },
            label = {
                Text(
                    text = "학습자",
                    style = TextStyle(
                        fontFamily = FontFamily(pretendardMediumFont),
                        color = if (currentRoute == "learner") MainGreen else GrayW40,
                        fontSize = 12.sp
                    )
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == "solution",
            onClick = { navController.navigate("solution") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.solution_nav),
                    contentDescription = "bottom navigation solution",
                    tint = if (currentRoute == "solution") MainGreen else GrayW40
                )
            },
            label = {
                Text(
                    text = "솔루션",
                    style = TextStyle(
                        fontFamily = FontFamily(pretendardMediumFont),
                        color = if (currentRoute == "solution") MainGreen else GrayW40,
                        fontSize = 12.sp
                    )
                )
            }
        )
        NavigationBarItem(
            selected = currentRoute == "calendar",
            onClick = { navController.navigate("calendar") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.calendar_nav),
                    contentDescription = "bottom navigation calendar",
                    tint = if (currentRoute == "calendar") MainGreen else GrayW40
                )
            },
            label = {
                Text(
                    text = "캘린더",
                    style = TextStyle(
                        fontFamily = FontFamily(pretendardMediumFont),
                        color = if (currentRoute == "calendar") MainGreen else GrayW40,
                        fontSize = 12.sp
                    )
                )
            }
        )
    }
}
