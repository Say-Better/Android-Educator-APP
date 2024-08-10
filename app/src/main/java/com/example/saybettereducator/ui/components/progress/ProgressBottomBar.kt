package com.example.saybettereducator.ui.components.progress

import android.os.Build
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
import com.example.saybettereducator.ui.theme.DarkGray
import com.example.saybettereducator.ui.theme.MainGreen
import com.example.saybettereducator.ui.theme.pretendardMediumFont

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProgressBottomBar() {
    var micClicked: Boolean by remember { mutableStateOf(false) }
    var cameraClicked: Boolean by remember { mutableStateOf(false) }

    Surface(
        color = DarkGray,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ){
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(width = 40.dp, height = 40.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable {
                        micClicked = !micClicked
                        //serviceRepository.toggleAudio(micClicked)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.mic),
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
                        //serviceRepository.toggleVideo(cameraClicked)
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cam),
                    contentDescription = "camera on/off",
                    tint = Color.White,
                    modifier = Modifier.padding(top = 14.dp, start = 10.dp)
                )
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(28.dp))
                    .alpha(0.8f)
                    .background(MainGreen)
                    .size(width = 152.dp, height = 40.dp)
                    .clickable { },
                horizontalArrangement = Arrangement.Absolute.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.solution_start_btn_icon),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(start = 21.5.dp)
                )
                Text(
                    text = "솔루션 시작",
                    fontSize = 16.sp,
                    color = Color.White,
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
                        //serviceRepository.switchCamera()
                    }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cam_switch),
                    contentDescription = "camera switch",
                    tint = Color.White,
                    modifier = Modifier.padding(top = 11.dp, start = 10.dp)
                )
            }
        }
    }
}