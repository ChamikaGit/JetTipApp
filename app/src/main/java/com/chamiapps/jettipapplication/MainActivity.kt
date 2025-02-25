package com.chamiapps.jettipapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.chamiapps.jettipapplication.util.calculateTipAmount
import com.chamiapps.jettipapplication.util.calculateTotalAmountPerPerson
import com.chamiapps.jettipapplication.widget.RoundIconButton

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
        containerColor = Color(0xFFF0E8A8)
    ) { innerPadding ->
        MainScreen(Modifier.padding(innerPadding))
    }
}

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    var totalAmountPerPerson by remember { mutableStateOf(0.0) }
    var totalBill by remember { mutableStateOf("") }
    var splitBy by remember { mutableStateOf(1) }
    var sliderPosition by remember { mutableStateOf(0f) }

    val tipPercentage = (sliderPosition * 100).toInt()
    val tipAmount = calculateTipAmount(tipPercentage = tipPercentage, totalBillState =totalBill.toDoubleOrNull() ?: 0.0)

    fun updateTotalAmountPerPerson() {
        totalAmountPerPerson = calculateTotalAmountPerPerson(
            tipPercentage = tipPercentage,
            totalBillState = totalBill.toDoubleOrNull() ?: 0.0,
            numberOfPeople = splitBy
        )
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            TopHeader(totalPerPerson = totalAmountPerPerson)
            BillForm(
                totalBill = totalBill,
                onTotalBillChange = {
                    totalBill = it
                    updateTotalAmountPerPerson()
                },
                splitBy = splitBy,
                onSplitByChange = {
                    splitBy = it
                    updateTotalAmountPerPerson()
                },
                sliderPosition = sliderPosition,
                onSliderChange = {
                    sliderPosition = it
                    updateTotalAmountPerPerson()
                },
                tipAmount = tipAmount
            )
        }
    }
}

@Composable
fun TopHeader(modifier: Modifier = Modifier, totalPerPerson: Double) {
    val totalFormatted = "%.2f".format(totalPerPerson)

    Card(
        modifier = modifier.padding(16.dp).fillMaxWidth().height(160.dp),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(containerColor = PurpleGrey40)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
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
                    text = "$$totalFormatted",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
        }
    }
}

@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    totalBill: String,
    onTotalBillChange: (String) -> Unit,
    splitBy: Int,
    onSplitByChange: (Int) -> Unit,
    sliderPosition: Float,
    onSliderChange: (Float) -> Unit,
    tipAmount: Double
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val isValidBill = totalBill.trim().isNotEmpty()

    Card(
        modifier = modifier.padding(8.dp).fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        border = BorderStroke(width = 2.dp, color = Color.Black)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CustomInputField(
                modifier = Modifier.fillMaxWidth(),
                valueState = remember { mutableStateOf(totalBill) },
                labelId = "Enter Bill",
                leadingIcon = {
                    Icon(imageVector = Icons.Rounded.Money, contentDescription = "Money Icon", tint = Color.Black)
                },
                onAction = KeyboardActions {
                    if (isValidBill) keyboardController?.hide()
                },
                onValueChange = onTotalBillChange
            )

            if (isValidBill) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text("Split", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
                    Spacer(modifier = Modifier.width(80.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RoundIconButton(
                            buttonIcon = Icons.Rounded.Remove,
                            onClickButton = {
                                if (splitBy > 1) onSplitByChange(splitBy - 1)
                            }
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text("$splitBy", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
                        Spacer(modifier = Modifier.width(20.dp))
                        RoundIconButton(
                            onClickButton = {
                                if (splitBy < 100) onSplitByChange(splitBy + 1)
                            }
                        )
                    }
                }

                Row(modifier = Modifier.padding(10.dp)) {
                    Text("Tip", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
                    Spacer(modifier = Modifier.width(200.dp))
                    Text("$$tipAmount", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
                }

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Slider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = sliderPosition,
                        onValueChange = onSliderChange,
                        steps = 5,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color.DarkGray,
                            inactiveTrackColor = Color.LightGray
                        )
                    )
                    Text("${(sliderPosition * 100).toInt()}%", style = MaterialTheme.typography.headlineSmall.copy(color = Color.Black))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    JetTipApplicationTheme {
        MyApp()
    }
}
