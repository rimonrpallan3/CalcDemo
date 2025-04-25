package com.example.caldemo.domain

import com.example.caldemo.helper.CalculatorAction
import com.example.caldemo.helper.CalculatorState

interface Calculator {
    fun calculate(state: CalculatorState, action: CalculatorAction): CalculatorState
}