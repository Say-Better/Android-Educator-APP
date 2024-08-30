package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.intent.SessionIntent
import com.example.saybettereducator.ui.theme.MainGreen

@Composable
fun ReadyHelloBtn(onHelloClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 35.dp)
            .clickable {

            }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.hellobtn),
            contentDescription = null,
            tint = MainGreen,
            modifier = Modifier
                .width(72.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.hello),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(start = 18.dp, top = 18.dp)
        )
    }
}