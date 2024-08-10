package com.example.saybettereducator.ui.components.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.saybettereducator.R

@Composable
fun ProgressBasicBtn(
    resId: Int,
    desc: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(colorResource(id = R.color.grey_100), shape = RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(start = 8.dp, end = 12.dp, top = 4.dp, bottom = 4.dp)
    ) {
        Icon(
            painter = painterResource(id = resId),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = stringResource(desc),
            color = Color.White,
            fontSize = 14.sp,
            lineHeight = 18.2.sp,
            fontWeight = FontWeight(500),
            textAlign = TextAlign.Center
        )
    }
}