package com.example.saybettereducator.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.saybettereducator.R
import com.example.saybettereducator.ui.theme.Black
import com.example.saybettereducator.ui.theme.Gray
import com.example.saybettereducator.ui.theme.PretendardTypography

@Composable
fun NameInputBox(
    nameState: String, onNameChange: (String) -> Unit
) {
    Text(
        text = "이름",
        style = PretendardTypography.bodySmall.copy(Gray),
        modifier = Modifier.padding(bottom = 12.dp)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .border(
                width = 1.dp, color = Black, shape = RoundedCornerShape(size = 12.dp)
            )
            .fillMaxWidth()
            .height(48.dp)
    ) {
        BasicTextField(
            value = nameState,
            onValueChange = { onNameChange(it) },
            textStyle = PretendardTypography.bodyMedium.copy(Black),
            singleLine = true,
            maxLines = 1,
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f),
        )

        Image(painter = painterResource(id = R.drawable.cancel_button),
            contentDescription = "cancell button",
            contentScale = ContentScale.None,
            modifier = Modifier
                .padding(end = 16.dp)
                .align(Alignment.CenterVertically)
                .clickable { onNameChange("교육자") })
    }
}