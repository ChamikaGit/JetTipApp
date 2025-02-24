package com.chamiapps.jettipapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chamiapps.jettipapplication.components.CustomInputField
import com.chamiapps.jettipapplication.ui.theme.JetTipApplicationTheme
import com.chamiapps.jettipapplication.ui.theme.PurpleGrey40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetTipApplicationTheme {
                MyApp()
            }
        }
    }
}

@Composable
fun MyApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        MainScreen(Modifier.padding(innerPadding))
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            TopHeader()
            MainContent()
        }
    }
}

@Preview
@Composable
fun TopHeader(modifier: Modifier = Modifier, totalPerPerson: Double = 0.0) {

    val total = "%.2f".format(totalPerPerson)

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey40)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Total Per Person",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "$${total}",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }

        }

    }
}

@Preview
@Composable
fun MainContent(modifier: Modifier = Modifier) {

    val totalBillState = remember {
        mutableStateOf("")
    }

    val isValidState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val keyBoardController = LocalSoftwareKeyboardController.current

    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 2.dp, color = Color.Black)
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {

            CustomInputField(valeState = totalBillState, labelId = "Enter Bill",
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Money, contentDescription = "Money Icon")
                },
                onAction = KeyboardActions {
                    if (!isValidState) {
                        return@KeyboardActions
                    } else {
                        keyBoardController?.hide()
                    }
                })


        }

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetTipApplicationTheme {
        MyApp()
    }
}
