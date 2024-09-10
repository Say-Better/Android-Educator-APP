package com.example.saybettereducator.ui.components.session

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Gray5B
import com.example.saybettereducator.ui.theme.MainGreen

@Composable
fun ReadyHelloBtn(greetState: Boolean, onHelloClick: () -> Unit) {
    Box(
        modifier = Modifier
            .padding(top = 35.dp)
            .size(72.dp)
            .background(
                color = if (greetState) Gray5B else MainGreen,
                shape = RoundedCornerShape(36.dp)
            )
            .clickable {
                onHelloClick()
            }
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_hello),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
