package com.example.caldemo.helper

data class CalculatorState(
    val number1: String = "0",
    val number2: String = "",
    val operation: CalculatorOperation? = null
)