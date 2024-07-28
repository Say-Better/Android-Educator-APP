package com.example.saybettereducator.ui.userinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.BoxBackground

@Composable
fun UserInfoScreen() {
    Surface(
        color = BoxBackground
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            ProfileImageCard()
        }
    }
}

@Composable
fun ProfileImageCard() {
    val showInputPopup = remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .padding(0.dp)
            .width(176.12549.dp)
            .height(176.005.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.educator_profile),
            contentDescription = "Educator Profile Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.grey_100),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(160.dp)
                .height(160.dp)
                .clip(RoundedCornerShape(size = 24.dp))
        )

        Image(
            painter = painterResource(id = R.drawable.profile_button),
            contentDescription = "Profile Button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(0.dp)
                .width(44.dp)
                .height(44.dp)
                .align(Alignment.BottomEnd)
                .clip(RoundedCornerShape(50))
                .clickable { showInputPopup.value = true }
        )

        if (showInputPopup.value) {
            ProfileInputPopup(showInputPopup)
        }
    }

}

@Composable
private fun ProfileInputPopup(showInputPopup: MutableState<Boolean>) {
    Popup(
        alignment = Alignment.Center,
        onDismissRequest = { showInputPopup.value = false }
    ) {
        Surface(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            color = Color.White
        ) {
            // Popup content goes here
            Box(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Popup Content")
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun UserInfoViewPreview() {
    UserInfoScreen()
}