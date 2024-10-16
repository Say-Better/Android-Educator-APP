package com.example.saybettereducator.ui.components.session

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R
import com.example.saybettereducator.data.repository.MainServiceRepository
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.Red
import com.example.saybettereducator.ui.theme.White
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SessionBottomBar(
    isStart: Boolean,
    isEnding: Boolean,
    serviceRepository: MainServiceRepository,
    onStartSolution: () -> Unit,
    onEndingSolution: () -> Unit,
    onTerminateSolution: () -> Unit,
) {
    var micClicked: Boolean by remember { mutableStateOf(false) }
    var cameraClicked: Boolean by remember { mutableStateOf(false) }

    Surface(
        color = DarkGray,
        modifier =
        if (!isStart)
            Modifier
                .fillMaxWidth()
                .height(109.dp)
                .background(Color.Black)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
        else Modifier
            .fillMaxWidth()
            .height(80.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = if (!isStart) 37.dp else 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable {
                        micClicked = !micClicked
                        serviceRepository.toggleAudio(micClicked)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_mic),
                    contentDescription = "mic on/off",
                    tint = Color.White,
                    modifier = Modifier.padding(top = 8.dp, start = 8.dp)
                )
            }

            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable {
                        cameraClicked = !cameraClicked
                        serviceRepository.toggleVideo(cameraClicked)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cam),
                    contentDescription = "camera on/off",
                    tint = Color.White,
                    modifier = Modifier.padding(top = 14.dp, start = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp))
                    .background(if (!isEnding) MainGreen else Red)
                    .size(width = 152.dp, height = 40.dp)
                    .clickable {
                        if (!isStart) {
                            onStartSolution()
                        } else if(!isEnding) {
                            onEndingSolution()
                        } else {
                            onTerminateSolution()
                        }

                    },
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_solution_start_btn_icon),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.padding(start = 21.5.dp)
                )
                Text(
                    text = if(!isStart) "솔루션 시작" else if(!isEnding) "솔루션 종료" else "통화 종료",
                    fontSize = 16.sp,
                    color = White,
                    fontFamily = FontFamily(pretendardMediumFont),
                    modifier = Modifier.padding(end = 21.5.dp)
                )
            }
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable {
                        serviceRepository.switchCamera()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_cam_switch),
                    contentDescription = "camera switch",
                    tint = Color.White,
                    modifier = Modifier.padding(top = 11.dp, start = 10.dp)
                )
            }
        }
    }
}