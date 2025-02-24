package com.chamiapps.jettipapplication.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Shop
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomInputField(
    modifier: Modifier = Modifier,
    valeState: MutableState<String>,
    labelId: String,
    enabled: Boolean = true,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Number,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable () -> Unit
) {
    OutlinedTextField(
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp),
        value = valeState.value,
        onValueChange = {
            valeState.value = it
        },
        label = { Text(text = labelId) },
        leadingIcon = leadingIcon,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground),
        enabled = enabled,
        singleLine = isSingleLine,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )
}
