package com.chamiapps.jettipapplication.util

fun calculateTipAmount(tipPercentage: Int, totalBillState: Double): Double {
    return if (totalBillState > 1 && totalBillState.toString().isNotEmpty()) {
        (totalBillState * tipPercentage) / 100
    } else {
        0.0
    }
}

fun calculateTotalAmountPerPerson(tipPercentage: Int, totalBillState: Double, numberOfPeople: Int): Double {
    val billAmount = calculateTipAmount(tipPercentage = tipPercentage, totalBillState = totalBillState) + totalBillState
    return billAmount / numberOfPeople
}