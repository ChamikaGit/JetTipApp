package com.chamiapps.jettipapplication.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun RoundIconButton(
    modifier: Modifier = Modifier,
    buttonIcon: ImageVector = Icons.Rounded.Add,
    onClickButton: () -> Unit = {}
) {


    Surface(
        modifier = Modifier
            .size(60.dp),
        shape = RoundedCornerShape(30.dp),
        color = Color.White,
        onClick = { onClickButton.invoke() },
        shadowElevation = 4.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = buttonIcon,
                contentDescription = "Round Button Icon",
                tint = Color.Black
            )
        }
    }
}